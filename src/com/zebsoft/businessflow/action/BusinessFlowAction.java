package com.zebsoft.businessflow.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zebsoft.businessflow.domain.Businessflow;
import com.zebsoft.businessflow.service.BusinessflowService;
import com.zebsoft.profitreport.action.DeviceProfitAction;
import com.zebsoft.right.domain.Users;
import com.zebsoft.zzz.util.ConfigInfo;
import com.zebsoft.zzz.util.ExcelGetData;
import com.zebsoft.zzz.util.LogHelper;
import com.zebsoft.zzz.util.MyUtils;
import com.zebsoft.zzz.util.SystemState;

@Controller("businessFlowAction")
@Scope("prototype")
public class BusinessFlowAction extends BaseAction{
	
	private static final Logger log = LoggerFactory.getLogger(BusinessFlowAction.class);	
	private static final String servicetype1 = ConfigInfo.getValue("SERVICECODE1");
	private static final String servicetype2 = ConfigInfo.getValue("SERVICECODE2");
	/**
	 * 分页显示所有交易流水（管理员）
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String queryBusinessflow() throws Exception{
		try {
			this.success = false;
			Map<String, Object> map = businessflowService.queryBusinessflow(start, limit);
			items_Businessflow = (List<Businessflow>) map.get("items");
			totalProperty = (Long) map.get("count");
			this.success = true;
		} catch (Exception e) {
			log.error("queryBusinessflow Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 分页显示用户自己的交易流水
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String queryFlowByUser() throws Exception{
		try {
			this.success = false;
			Map<String, Object> map = businessflowService.queryFlowByUser(start, limit);
			items_Businessflow = (List<Businessflow>) map.get("items");
			totalProperty = (Long) map.get("count");
			this.success = true;
		} catch (Exception e) {
			log.error("queryFlowByUser Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	/**
	 *条件查询交易流水(条件包括服务编号，手机号（客户编号），交易开始时间，结束时间)
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String selectBusinessflow() throws Exception{
		try {
			this.success = false;
			Map<String, Object> map = businessflowService.selectBusinessflow(start, limit,businessflow);
			items_Businessflow = (List<Businessflow>) map.get("items");
			totalProperty = (Long) map.get("count");
			this.success = true;
		} catch (Exception e) {
			log.error("selectBusinessflow Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 *用户条件查询交易流水(条件包括服务编号，交易开始时间，结束时间)
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String selectFlowByUser() throws Exception{
		try {
			this.success = false;
			Map<String, Object> map = businessflowService.selectFlowByUser(start, limit,businessflow);
			items_Businessflow = (List<Businessflow>) map.get("items");
			totalProperty = (Long) map.get("count");
			this.success = true;
		} catch (Exception e) {
			log.error("selectFlowByUser Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除交易流水（支持多条删除）
	 * @return
	 * @throws Exception 
	 */
	public String delBusinessflow() throws Exception{
		try {
			this.success = false;
			if(businessflowService.delBusinessflow(allData)){
				this.success = true;
			}
			else{
				this.success = false;
			}
		} catch (Exception e) {
			log.error("delBusinessflow Exception:"+e.getMessage(),e);
		}
		return SUCCESS;	
	}
	
	/**
	 * 删除文件
	 * @author 张恩备
	 * @date 2016-3-13 下午02:39:31
	 */
	public void doDeleteFileAfter(String filepath) {
		try {
			File file = new File(filepath);
			if (file.exists()) {
				file.delete();
			}
			file = null;
		} catch (Exception e) {
			log.error("doDeleteFileAfter Exception:"+e.getMessage(),e);
		}
	}
	
	/**
	 * 上传excel文件到服务器
	 * @author 张恩备
	 * @date 2016-3-13 下午12:31:18
	 */
	public String uploadfile() throws Exception {
		this.success = false;
		log.info("---upload file start---");
		String rootPath = getSession().getServletContext().getRealPath("/");
		String ROOT = "upload\\temp\\";

		MyUtils.mkDirectory(rootPath + ROOT);

		String newFileName = null;
		try {
			newFileName = MyUtils.upload(getUploadFlowFileName(), rootPath + ROOT, getUploadFlowFile());
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			log.error("upload file Exception:"+e.getMessage(),e);
		}

		if (newFileName == null) {
			newFileName = "";
		}
		// 本地路径
		String filepath = rootPath + ROOT + newFileName;
		getSession().setAttribute("newFileName", filepath);
		// salaryRecordService.importSalary(filepath, this.checkidanddate);
		this.success = true;
		// doDeleteFileAfter(filepath);
		if (BrowserInformation.equals("IE: 8.0") || BrowserInformation.equals("IE: 9.0")) {

			return NONE;
		} else {

			return SUCCESS;
		}
	}
	
	/**
	 * 将上传excel展现到前台
	 * @author 张恩备
	 * @date 2016-3-13 下午02:36:58
	 */
	public String showRecord() throws Exception {
		this.success = false;
		Map<String, Object> map = new HashMap<String, Object>();
		String[] salaryType = { "服务编码", "交易编码", "交易日期", "交易时间", "交易流水", "本地清算日期", "客户编码", "手机号码", "交易渠道", "终端编码",
											"PSAM卡号", "业务受理方", "原始业务流水", "原始业务参考号", "原始业务日期", "原始业务时间", "原始业务结算日期", "发送方业务流水", 
											"发送方业务日期","发送方业务时间", "交易主账号", "第二交易账号", "交易金额(元)", "交易手续费(元) ", "交易状态", "交易标记", "备注说明"};

		try {
			items_Businessflow = new ArrayList<Businessflow>();
			String filepath = (String) getSession().getAttribute("newFileName");
			File excel = new File(filepath);
			String[][] st = new ExcelGetData().getData(excel, 0, 0);
			String[] s = st[0];
			for (int i = 0; i < salaryType.length; i++) {
				for (int j = 0; j < s.length; j++) {
					if (s[j].trim().equals(salaryType[i].trim())) {
						map.put(String.valueOf(i), j);
						break;
					}
				}
			}

			for (int i = 1; i < st.length; i++) {
				Businessflow businessflow = new Businessflow();
				String paydesc = null ;
				if(map.get("26") != null && st[i][(Integer) map.get("26")] != null && !st[i][(Integer) map.get("26")].equals("")){
					if(st[i][(Integer) map.get("26")].contains("1")){
						paydesc=servicetype1;
					}else if(st[i][(Integer) map.get("26")].contains("0.49")){
						paydesc=servicetype2;
					}else if(st[i][(Integer) map.get("26")].contains("0.005")){
						paydesc=servicetype2;
					}
				}else{
					this.success = false;
					return SUCCESS;
				}
				//判断哪些数据不为空即可保存数据
				if (map.get("6") != null && st[i][(Integer) map.get("6")] != null && !st[i][(Integer) map.get("6")].equals("")) {
					businessflow.setServicecode(map.get("0") != null ? st[i][(Integer) map.get("0")] : "");
					businessflow.setPaycode(map.get("1") != null ? st[i][(Integer) map.get("1")] : "");
					businessflow.setPaydate(map.get("2") != null ? st[i][(Integer) map.get("2")] : "");
					businessflow.setPaytime(map.get("3") != null ? st[i][(Integer) map.get("3")] : "");
					businessflow.setTradingflow(map.get("4") != null ? st[i][(Integer) map.get("4")] : "");
					businessflow.setLocaldate(map.get("5") != null ? st[i][(Integer) map.get("5")] : "");
					businessflow.setUsercode(map.get("6") != null ? st[i][(Integer) map.get("6")] : "");
					businessflow.setUseriphone(map.get("7") != null ? st[i][(Integer) map.get("7")] : "");
					businessflow.setTradingchannel(map.get("8") != null ? st[i][(Integer) map.get("8")] : "");
					businessflow.setEndcode(map.get("9") != null ? st[i][(Integer) map.get("9")] : "");
					businessflow.setPsamcard(map.get("10") != null ? st[i][(Integer) map.get("10")] : "");
					businessflow.setBusacquirer(map.get("11") != null ? st[i][(Integer) map.get("11")] : "");
					businessflow.setBusflow(map.get("12") != null ? st[i][(Integer) map.get("12")] : "");
					businessflow.setBusnumber(map.get("13") != null ? st[i][(Integer) map.get("13")] : "");
					businessflow.setBusdate(map.get("14") != null ? st[i][(Integer) map.get("14")] : "");
					businessflow.setBustime(map.get("15") != null ? st[i][(Integer) map.get("15")] : "");
					businessflow.setBusenddate(map.get("16") != null ? st[i][(Integer) map.get("16")] : "");
					businessflow.setSendbusflow(map.get("17") != null ? st[i][(Integer) map.get("17")] : "");
					businessflow.setSendbusdate(map.get("18") != null ? st[i][(Integer) map.get("18")] : "");
					businessflow.setSendbustime(map.get("19") != null ? st[i][(Integer) map.get("19")] : "");
					businessflow.setPaymainacc(map.get("20") != null ? st[i][(Integer) map.get("20")] : "");
					businessflow.setPaysecondacc(map.get("21") != null ? st[i][(Integer) map.get("21")] : "");
					businessflow.setPaymoney(Float.valueOf(map.get("22") != null ? st[i][(Integer) map.get("22")] : ""));
					businessflow.setPayfee(Float.valueOf(map.get("23") != null ? st[i][(Integer) map.get("23")] : ""));
					businessflow.setPaystate(map.get("24") != null ? st[i][(Integer) map.get("24")] : "");
					businessflow.setPaymark(map.get("25") != null ? st[i][(Integer) map.get("25")] : "");
					businessflow.setPaydesc(map.get("26") != null ? paydesc : "");
					if ( businessflow == null) {
						this.success = false;
						return SUCCESS;
					} else {
						items_Businessflow.add(businessflow);
					}
				} else {
					this.success = false;
					return SUCCESS;
				}
			}
			this.success = true;
		} catch (Exception e) {
			this.success = false;
			log.error("showRecord Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}

	/**
	 * 将数据保存到数据库
	 * @author 张恩备
	 * @date 2016-3-13 下午02:39:50
	 */
	public String addBusinessFlowRecord() throws Exception {
		this.success = false;
		JSONArray array = JSONArray.fromObject(allData);
		int j=0;
		int n=0;
		String pp ="";
		for (int i = 0; i < array.size(); i++) {
			try {
				JSONObject object = array.getJSONObject(i);
				//以交易流水作为主键，判断数据库是否已存在
				String tradingflow = object.get("tradingflow").toString();
				List<Businessflow> bfList = businessflowService.findByTradingflow(tradingflow);
				if(bfList.size() == 0){					
					Businessflow businessflow = new Businessflow();
					businessflow.setServicecode(object.get("servicecode").toString());
					businessflow.setPaycode(object.get("paycode").toString());
					businessflow.setPaydate(object.get("paydate").toString());
					businessflow.setPaytime(object.get("paytime").toString());
					businessflow.setTradingflow(object.get("tradingflow").toString());
					businessflow.setLocaldate(object.get("localdate").toString());
					businessflow.setUsercode(object.get("usercode").toString());
					businessflow.setUseriphone(object.get("useriphone").toString());
					businessflow.setTradingchannel(object.get("tradingchannel").toString());
					businessflow.setEndcode(object.get("endcode").toString());
					businessflow.setPsamcard(object.get("psamcard").toString());
					businessflow.setBusacquirer(object.get("busacquirer").toString());
					businessflow.setBusflow(object.get("busflow").toString());
					businessflow.setBusnumber(object.get("busnumber").toString());
					businessflow.setBusdate(object.get("busdate").toString());
					businessflow.setBustime(object.get("bustime").toString());
					businessflow.setBusenddate(object.get("busenddate").toString());
					businessflow.setSendbusflow(object.get("sendbusflow").toString());
					businessflow.setSendbusdate(object.get("sendbusdate").toString());
					businessflow.setSendbustime(object.get("sendbustime").toString());
					businessflow.setPaymainacc(object.get("paymainacc").toString());
					businessflow.setPaysecondacc(object.get("paysecondacc").toString());
					businessflow.setPaymoney(Float.valueOf(object.get("paymoney").toString()));
					businessflow.setPayfee(Float.valueOf(object.get("payfee").toString()));
					businessflow.setPaystate(object.get("paystate").toString());
					businessflow.setPaymark(object.get("paymark").toString());
					businessflow.setPaydesc(object.get("paydesc").toString());
					businessflow.setState(SystemState.USE);
					if(businessflowService.save(businessflow)){
						j++;
					}else{						
						pp+=tradingflow+",";
					}
				}else{
					n++;
				}
			}catch (Exception e) {
				log.error("addBusinessFlowRecord Exception:"+e.getMessage(),e);
			}
		}
		errorData=pp;
		allData ="总记录数："+array.size()+"，成功上传数："+j+"，数据库已存在数："+n;
		this.success = true;
		return SUCCESS;
	}

	/*****************类方法（↑）********************/
	
	/*****************类属性（↓）********************/
	private Integer start;
	private Integer limit;
	private String allData;
	private boolean success;
	private Businessflow businessflow;
	private Long totalProperty;
	private List<Businessflow> items_Businessflow;
	private File uploadFlowFile;
	private String uploadFlowFileName;
	private String BrowserInformation;
	private String errorData;
	@Resource
	private BusinessflowService businessflowService;

	/*****************类属性（↑）********************/	
	
	/*****************类getset方法（↓）********************/
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getAllData() {
		return allData;
	}

	public void setAllData(String allData) {
		this.allData = allData;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Businessflow getBusinessflow() {
		return businessflow;
	}

	public void setBusinessflow(Businessflow businessflow) {
		this.businessflow = businessflow;
	}

	public Long getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(Long totalProperty) {
		this.totalProperty = totalProperty;
	}

	public List<Businessflow> getItems_Businessflow() {
		return items_Businessflow;
	}

	public void setItems_Businessflow(List<Businessflow> items_Businessflow) {
		this.items_Businessflow = items_Businessflow;
	}

	public File getUploadFlowFile() {
		return uploadFlowFile;
	}

	public void setUploadFlowFile(File uploadFlowFile) {
		this.uploadFlowFile = uploadFlowFile;
	}

	public String getBrowserInformation() {
		return BrowserInformation;
	}

	public void setBrowserInformation(String browserInformation) {
		BrowserInformation = browserInformation;
	}

	public String getUploadFlowFileName() {
		return uploadFlowFileName;
	}

	public void setUploadFlowFileName(String uploadFlowFileName) {
		this.uploadFlowFileName = uploadFlowFileName;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	
}
