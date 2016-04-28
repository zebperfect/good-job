package com.zebsoft.monthsettlement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zebsoft.businessflow.DAO.BusinessflowDAO;
import com.zebsoft.businessflow.domain.Businessflow;
import com.zebsoft.monthsettlement.DAO.MonthsettlementDAO;
import com.zebsoft.monthsettlement.domain.MonthDTO;
import com.zebsoft.monthsettlement.domain.Monthsettlement;
import com.zebsoft.profitreport.service.DeviceProfitService;
import com.zebsoft.profitreport.service.ProfitReportService;
import com.zebsoft.right.DAO.UsersDAO;
import com.zebsoft.right.domain.Users;
import com.zebsoft.zzz.util.CommonsMethod;
import com.zebsoft.zzz.util.MyUtils;
import com.zebsoft.zzz.util.SystemState;

@Service("monthsettlementService")
public class MonthsettlementService {

	private static final Logger log = LoggerFactory.getLogger(MonthsettlementService.class);
	@Resource
	private MonthsettlementDAO monthsettlementDAO;
	@Resource
	private UsersDAO usersDAO;
	@Resource
	private BusinessflowDAO businessflowDAO;
	@Resource
	private ProfitReportService profitReportService;
	@Resource
	private DeviceProfitService deviceProfitService;
	/**
	 * 条件查询月结清单（条件包括手机号码，月结月份）
	 * @author 张恩备
	 * @date 2016-4-21 下午12:13:21
	 */
	public List<MonthDTO> selectSettlement(Integer start, Integer limit, String queryTelephone,String queryDate){
		List<MonthDTO> mdto = new ArrayList<MonthDTO>();
		
		List<Monthsettlement> msList = monthsettlementDAO.selectSettlement(start,limit,queryTelephone,queryDate);
		for(Monthsettlement ms : msList){
			MonthDTO mt = new MonthDTO();
			Users  u = usersDAO.findByUserId(ms.getUserid());
			if(u != null){
				mt.setUsername(u.getUsername());
				mt.setUsercard(u.getUsercard());
				mt.setBankname(u.getBankname());
				mt.setBankcard(u.getBankcard());
				mt.setBankaddress(u.getBankaddress());
				//暂不使用ms记录的usercode
				mt.setUsercode(u.getUsercode());
			}else{
				mt.setUsercode("用户不存在或已删除");
			}
			mt.setTelephone(ms.getTelephone());
			mt.setProfitmoney(ms.getProfitmoney());
			mt.setDevicemoney(ms.getDevicemoney());
			mt.setTotalmoney(ms.getTotalmoney());
			mt.setInmonth(ms.getInmonth());
			mdto.add(mt);
		}
		return mdto;
	}
	/**
	 * 根据手机号、日期导出
	 * @author 张恩备
	 * @date 2016-4-22 下午03:54:14
	 */
	public String exportExcel(String queryTelephone, String queryDate) {
		List<Object[]> ObjItems = new ArrayList<Object[]>();
		Object[] ob = { "手机号", "客户编码", "姓名", "身份证号", "开户银行", "开户银行卡号", "开户地址","分润额","提成","总额","月份"};
		ObjItems.add(ob);
		if((queryDate == null || "".equals(queryDate) || queryDate.contains("'")) && 
				(queryTelephone == null || "".equals(queryTelephone) || queryTelephone.contains("'"))){
			return null;
		}
		List<Object[]> tempList = monthsettlementDAO.getExcelData(queryTelephone,queryDate);
		ObjItems.addAll(tempList);
		String excelName = "money"+CommonsMethod.getNowCorrect2Second()+ ".xls";
		String rootPath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
		String filePath = "tempExcel/" + excelName;
		MyUtils.mkDirectory(rootPath + "tempExcel/");
		MyUtils.putExcel(rootPath, filePath, "", ObjItems);
		return filePath;
	}
	/**
	 * 定时结算,未判断当月是否存在重复数据
	 * @author 张恩备
	 * @date 2016-4-22 下午05:16:13
	 */
	public void settleByMonthJob() {
		String lastMonth = CommonsMethod.getLastMonth()+"01";
		String nowMonth = CommonsMethod.getNowMonth()+"01";
		log.info("settlement month : "+lastMonth+"----now time : "+CommonsMethod.getNowCorrect2SecondWithWord());
		if(monthsettlementDAO.findInmonth(lastMonth.substring(0, 6))){
			log.info("----当前结算月份已做结算，不再进行统计----");
			return ;
		}
		//拉取所有可用用户
		List<Users> tempList = usersDAO.getAllUsers();
		if(tempList.size() == 0){
			return ;
		}
		Businessflow flow = new Businessflow();
		//20160426
		flow.setPaydate(lastMonth);
		flow.setPaytime(nowMonth);
		for(Users u : tempList){
			Monthsettlement ms = new Monthsettlement();
			ms.setUserid(u.getId());
			ms.setTelephone(u.getTelephone());
			ms.setUsercode(u.getUsercode());
			ms.setInmonth(lastMonth.substring(0, 6));
			ms.setState(SystemState.USE);
			//分润
			float profit =0;
			if(u.getUsercode() != null && !"".equals(u.getUsercode())){				
				flow.setUsercode(u.getUsercode());
				//得到所有交易流水
				List<Object[]> objects =businessflowDAO.partFlowByUser(flow);
				profit = profitReportService.profitTotal(objects);
				ms.setProfitmoney(profit);
			}else{
				ms.setUsercode("无客户编码");
				log.info("-用户："+u.getUsername()+"--没有绑定客户编码--");
			}
			Users tu = new Users();
			//用户提成（时间段usercard,bankcard,20160426105137）
			tu.setUsercard(lastMonth+"01000000");
			tu.setBankcard(nowMonth+"01000000");
			tu.setId(u.getId());
			tu.setTelephone(u.getTelephone());
			tu.setUsername(u.getUsername());
			tu.setDepth(u.getDepth());
			tu.setPath(u.getPath());
			float deviceProfit = deviceProfitService.deviceProfitTotal(tu);
			ms.setDevicemoney(deviceProfit);
			//总额
			float ptotal = profit+deviceProfit;
			ms.setTotalmoney(ptotal);
			monthsettlementDAO.save(ms);
		}
	}
	/**
	 * 用户自定义结算
	 * @author 张恩备
	 * @date 2016-4-25 下午06:05:38
	 */
	public List<MonthDTO> settleByUser(String userid, String startDate,String endDate) {
		List<MonthDTO> tempDTO = new ArrayList<MonthDTO>();
		if(userid == null || "".equals(userid) || startDate == null || "".equals(startDate)
				|| endDate == null || "".equals(endDate)){
			return tempDTO;
		}
		log.info("settlement date : "+startDate+"---"+endDate);
		Users user = usersDAO.findByUserId(Integer.valueOf(userid));
		if(user == null){
			return tempDTO;
		}
		MonthDTO md = new MonthDTO();
		md.setUserid(user.getId());
		md.setUsername(user.getUsername());
		md.setUsercard(user.getUsercard());
		md.setBankname(user.getBankname());
		md.setBankcard(user.getBankcard());
		md.setBankaddress(user.getBankaddress());
		md.setTelephone(user.getTelephone());
		//分润
		Businessflow flow = new Businessflow();
		flow.setPaydate(startDate);
		flow.setPaytime(endDate);		
		float profit =0;
		if(user.getUsercode() != null && !"".equals(user.getUsercode())){				
			flow.setUsercode(user.getUsercode());
			//得到所有交易流水
			List<Object[]> objects =businessflowDAO.partFlowByUser(flow);
			profit = profitReportService.profitTotal(objects);
			md.setUsercode(user.getUsercode());
			md.setProfitmoney(profit);
		}else{
			log.info("-用户："+user.getUsername()+"--没有绑定客户编码--");
			//加一条记录
			md.setUsercode("未绑定编码");
			return tempDTO;
		}
		//用户提成（时间段usercard,bankcard）
		Users tu = new Users();
		tu.setUsercard(startDate+"01000000");
		tu.setBankcard(endDate+"01000000");
		tu.setId(user.getId());
		tu.setTelephone(user.getTelephone());
		tu.setUsername(user.getUsername());
		tu.setDepth(user.getDepth());
		tu.setPath(user.getPath());
		float deviceProfit = deviceProfitService.deviceProfitTotal(tu);
		md.setDevicemoney(deviceProfit);
		//总额
		float ptotal = profit+deviceProfit;
		md.setTotalmoney(ptotal);
		md.setInmonth(startDate+"-"+endDate);
		tempDTO.add(md);
		return tempDTO;
	}
}
