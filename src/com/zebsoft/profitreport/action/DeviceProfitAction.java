package com.zebsoft.profitreport.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.profitreport.domain.UserDTO;
import com.zebsoft.profitreport.service.DeviceProfitService;
import com.zebsoft.right.domain.Users;

@Controller("deviceProfitAction")
@Scope("prototype")
public class DeviceProfitAction extends ActionSupport{
	private static final Logger log = LoggerFactory.getLogger(DeviceProfitAction.class);	
	
	/**
	 * 查询当前用户的设备提成
	 * @return  
	 * @throws Exception 
	 */
	public String selectProfitCount() throws Exception{
		try {
			this.success = false;
			items_UserDTO= deviceProfitService.selectProfitCount(start, limit, user);
			totalProperty = (long) items_UserDTO.size();
			this.success = true;
		} catch (Exception e) {
			log.error("selectProfitCount Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/*****************类方法（↑）********************/
	/*****************类属性（↓）********************/
	private Integer start;
	private Integer limit;
	private String delData;
	private boolean success;
	private Users user;
	private Long totalProperty;
	@Resource
	private DeviceProfitService deviceProfitService;
	
	private List<UserDTO> items_UserDTO;
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
	public String getDelData() {
		return delData;
	}
	public void setDelData(String delData) {
		this.delData = delData;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	public Users getUser() {
		return user;
	}
	public Long getTotalProperty() {
		return totalProperty;
	}
	public void setTotalProperty(Long totalProperty) {
		this.totalProperty = totalProperty;
	}
	public List<UserDTO> getItems_UserDTO() {
		return items_UserDTO;
	}
	
}
