package com.zebsoft.monthsettlement.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.monthsettlement.domain.MonthDTO;
import com.zebsoft.monthsettlement.service.MonthsettlementService;

@Controller("monthsettlementAction")
@Scope("prototype")
public class MonthsettlementAction extends ActionSupport{
	private static final Logger log = LoggerFactory.getLogger(MonthsettlementAction.class);

	/**
	 * 条件查询月结清单（条件包括手机号码，月结月份）
	 * @author 张恩备
	 * @date 2016-4-21 下午12:17:36
	 */
	public String selectSettlement() throws Exception{
		try {
			this.success = false;
			items_MonthDTO = monthsettlementService.selectSettlement(start, limit,queryTelephone,queryDate);
			totalProperty = (long) items_MonthDTO.size();
			this.success = true;
		} catch (Exception e) {
			log.error("selectBusinessflow Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 导出excel数据
	 * @return  
	 * @throws Exception 
	 */
	public String exportExcel() throws Exception{
		try {
			this.success = false;
			path= monthsettlementService.exportExcel(queryTelephone,queryDate);
			if(path != null){				
				this.success = true;
			}
		} catch (Exception e) {
			this.success = false;
			log.error("exportExcel Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	/**
	 * 用户自定义结算
	 * @author 张恩备
	 * @date 2016-4-22 下午04:47:35
	 */
	public String settlementByUser() throws Exception{
		try {this.success = false;
			long start = System.currentTimeMillis();
			log.info("-----------start settlementr by user-----------");
			items_MonthDTO = monthsettlementService.settleByUser(path,queryTelephone,queryDate);
			long end = System.currentTimeMillis();totalProperty = (long) items_MonthDTO.size();
			this.success = true;
			log.info("-----------end settlement by user--"+"运行时间：" + (end - start) + "毫秒"+"---------");
		} catch (Exception e) {
			log.error("settlementByUser Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 定时每月2号做所有用户的结算
	 * @author 张恩备
	 * @date 2016-4-22 下午04:47:35
	 */
	public void settleByMonthJob(){
		long start = System.currentTimeMillis();
		log.info("-----------start settlement by month job-----------");
		monthsettlementService.settleByMonthJob();
		long end = System.currentTimeMillis();
		log.info("-----------end settlement by month job--"+"运行时间：" + (end - start) + "毫秒"+"---------");
	}
	@Resource
	private MonthsettlementService monthsettlementService;
	private boolean success;
	private Integer start;
	private Integer limit;
	private Long totalProperty;
	private String queryTelephone;
	private String queryDate;
	private String path;
	private List<MonthDTO> items_MonthDTO;

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
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
	public Long getTotalProperty() {
		return totalProperty;
	}
	public void setTotalProperty(Long totalProperty) {
		this.totalProperty = totalProperty;
	}
	public void setQueryTelephone(String queryTelephone) {
		this.queryTelephone = queryTelephone;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public List<MonthDTO> getItems_MonthDTO() {
		return items_MonthDTO;
	}
	public void setItems_MonthDTO(List<MonthDTO> items_MonthDTO) {
		this.items_MonthDTO = items_MonthDTO;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
