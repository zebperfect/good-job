package com.zebsoft.profitreport.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.profitreport.domain.FlowReport;
import com.zebsoft.profitreport.service.ProfitReportService;

/**
 * 分润月报控制层
 * @author 张恩备
 * @date 2016-3-15 下午06:42:50
 */
@Controller("profitReportAction")
@Scope("prototype")
public class ProfitReportAction extends ActionSupport{

	private static final Logger log = LoggerFactory.getLogger(ProfitReportAction.class);	
	/**
	 * 显示当前用户的月报
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String queryReportByUser() throws Exception{
		try {
			this.success = false;
			items_FlowReports = profitReportService.queryReportByUser();
			totalProperty = (long) items_FlowReports.size();
			this.success = true;
		} catch (Exception e) {
			log.error("queryReportByUser Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 条件查询用户的月报
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String selectFlowReport() throws Exception{
		try {
			this.success = false;
			items_FlowReports = profitReportService.selectFlowReport(flowReport);
			totalProperty = (long) items_FlowReports.size();
			this.success = true;
		} catch (Exception e) {
			log.error("selectFlowReport Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}

	/*****************类方法（↑）********************/
	
	/*****************类属性（↓）********************/
	private Integer start;
	private Integer limit;
	private String allData;
	private boolean success;
	private FlowReport flowReport;
	private Long totalProperty;
	private List<FlowReport> items_FlowReports;
	@Resource
	private ProfitReportService profitReportService;

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
	public FlowReport getFlowReport() {
		return flowReport;
	}
	public void setFlowReport(FlowReport flowReport) {
		this.flowReport = flowReport;
	}
	public Long getTotalProperty() {
		return totalProperty;
	}
	public void setTotalProperty(Long totalProperty) {
		this.totalProperty = totalProperty;
	}
	public List<FlowReport> getItems_FlowReports() {
		return items_FlowReports;
	}
	public void setItems_FlowReports(List<FlowReport> items_FlowReports) {
		this.items_FlowReports = items_FlowReports;
	}

}
