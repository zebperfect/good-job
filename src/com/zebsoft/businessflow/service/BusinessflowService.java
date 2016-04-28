package com.zebsoft.businessflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.zebsoft.businessflow.DAO.BusinessflowDAO;
import com.zebsoft.businessflow.domain.Businessflow;
import com.zebsoft.right.domain.Users;

@Service("businessflowService")
public class BusinessflowService {

	@Resource
	private BusinessflowDAO businessflowDAO;
	/**
	 * 得到当前操作的管理员信息
	 * 
	 * @author Thinker
	 * @return
	 */
	public Users getUser() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		Users user = (Users) session.get("user");
		return user;
	}
	
	/**
	 * 分页显示所有交易流水（管理员）
	 * 
	 * @return
	 */
	public Map<String, Object> queryBusinessflow(Integer start, Integer limit){
		return businessflowDAO.queryBusinessflow(start,limit);
	}
	/**
	 * 分页显示用户自己的交易流水
	 * 
	 * @return
	 */
	public Map<String, Object> queryFlowByUser(Integer start, Integer limit){
		Map<String , Object> map = new HashMap<String, Object>();
		String usercode = getUser().getUsercode();
		if(usercode != null && !"".equals(usercode)){
			Businessflow flow = new Businessflow();
			flow.setUsercode(usercode);
			map = businessflowDAO.selectBusinessflow(start,limit,flow);
		}
		return map;
	}
	
	/**
	 * 保存数据
	 */
	public boolean save(Businessflow businessflow){
		try {			
			businessflowDAO.save(businessflow);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 根据交易流水查找记录
	 * @author 张恩备
	 * @date 2016-3-13 下午04:00:54
	 */
	public List<Businessflow> findByTradingflow(String tradingflow){
		return businessflowDAO.findByTradingflow(tradingflow);
	}
	
	/**
	 * 根据payid查找实体
	 * @return 
	 */
	public Businessflow findByPayId(String payid){
		return businessflowDAO.findByPayId(payid);
	}
	
	/**
	 * 条件查询交易流水
	 * @return
	 */
	public Map<String , Object> selectBusinessflow(Integer start, Integer limit, Businessflow flow){
		return businessflowDAO.selectBusinessflow(start,limit,flow);
	}
	/**
	 * 用户条件查询交易流水
	 * @return
	 */
	public Map<String , Object> selectFlowByUser(Integer start, Integer limit, Businessflow flow){
		Map<String , Object> map = new HashMap<String, Object>();
		String usercode = getUser().getUsercode();
		if(usercode != null && !"".equals(usercode)){
			flow.setUsercode(usercode);
			map = businessflowDAO.selectBusinessflow(start,limit,flow);
		}
		return map;
	}
	
	/**
	 *  删除记录（支持多条删除）
	 * @param delData
	 * @return
	 * @throws Exception 
	 */
	public boolean delBusinessflow(String delData) throws Exception{
		 String tradingFlow = businessflowDAO.delBusinessflow(delData);
		 System.out.println(tradingFlow);
		 return true;
	}
}
