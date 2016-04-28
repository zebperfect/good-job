package com.zebsoft.profitreport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.zebsoft.businessflow.DAO.BusinessflowDAO;
import com.zebsoft.businessflow.domain.Businessflow;
import com.zebsoft.profitreport.domain.FlowReport;
import com.zebsoft.right.DAO.UsersDAO;
import com.zebsoft.right.domain.Users;
import com.zebsoft.zzz.util.CommonsMethod;
import com.zebsoft.zzz.util.ConfigInfo;

@Service("profitReportService")
public class ProfitReportService {
	@Resource
	private BusinessflowDAO businessflowDAO;
	@Resource
	private UsersDAO usersDAO;
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
	 * 查询当前用户的月报
	 * @author 张恩备
	 * @date 2016-3-16 下午03:35:54
	 */
	public List<FlowReport> queryReportByUser() {
		List<FlowReport> list = new ArrayList<FlowReport>();
		String usercode = getUser().getUsercode();
		String username = getUser().getUsername();
		if(usercode != null && !"".equals(usercode)){
			Businessflow flow = new Businessflow();
			flow.setUsercode(usercode);
			//默认时间段,今天到本月1号
			String today = CommonsMethod.getToday();
			String startdate = today.substring(0, 6).trim()+"01";
			flow.setPaydate(startdate);
			flow.setPaytime(today);
			//拉取关键字段
			List<Object[]> objects = businessflowDAO.partFlowByUser(flow);
			list = totalReport(objects, username,flow);
		}
		return list;
	}
	
	/**
	 * 条件查询用户的月报(层级(profitnum字段)，客户编号(username字段)，开始结束时间)
	 * @author 张恩备
	 * @date 2016-3-16 下午03:35:54
	 */
	public List<FlowReport> selectFlowReport(FlowReport flowreport) {
		List<FlowReport> ll = new ArrayList<FlowReport>();
		//判断时间是否超出三个月，暂不做
		Businessflow flow = new Businessflow();
		String username =null;
		String usercode=null;
		String today = CommonsMethod.getToday();
		String startdate = today.substring(0, 6).trim()+"01";
		if(flowreport != null){
			if(flowreport.getStartdate() != null && !"".equals(flowreport.getStartdate())){
				flow.setPaydate(flowreport.getStartdate());
			}else{
				//赋默认时间段，本月01号到今天
				flow.setPaydate(startdate);
			}
			if(flowreport.getEnddate() != null && !"".equals(flowreport.getEnddate())){
				flow.setPaytime(flowreport.getEnddate());
			}else{
				//赋默认时间段
				flow.setPaytime(today);
			}//查询客户编号
			if(flowreport.getUsername() != null && !"".equals(flowreport.getUsername())){
				List<Users> userList =  usersDAO.findByUserCode(flowreport.getUsername());
				if(userList.size() != 0){
					Users user = userList.get(0);
					username = user.getUsername();
					usercode = user.getUsercode();
					if(username ==null || "".equals(username)){
						return ll;
					}
					//层级
					if(flowreport.getProfitnum() != null){
						String tpath = user.getPath();
						Integer depth = user.getDepth();
						Integer userid = user.getId();
						if(tpath == null || depth == null || userid == null){
							return ll;
						}
						String path = tpath +"-"+userid ;
						Integer selectDepth = depth + flowreport.getProfitnum();//深度
						List<Object[]> countList = usersDAO.findCodeByUserPath(null,path,selectDepth);
						List<Object[]> ooList = new ArrayList<Object[]>();
						for(Object[] o : countList){
							usercode = (String)o[1];
							if(usercode != null && !"".equals(usercode)){								
								flow.setUsercode(usercode);
								List<Object[]> tempo = businessflowDAO.partFlowByUser(flow);
								ooList.addAll(tempo);
							}else{
								continue;
							}
						}
						return totalReport(ooList, username,flow);
					}else{					
						if(usercode != null && !"".equals(usercode)){
							flow.setUsercode(usercode);
							List<Object[]> objects = businessflowDAO.partFlowByUser(flow);
							return totalReport(objects, username,flow);
						}
					}
				}else{
					return ll;
				}
			}else{//当前用户查询				
				usercode = getUser().getUsercode();
				username = getUser().getUsername();
				if(username ==null || "".equals(username)){
					return ll;
				}
				//层级
				if(flowreport.getProfitnum() != null){
					String tpath = getUser().getPath();
					Integer depth = getUser().getDepth();
					if(tpath == null || depth == null){
						return ll;
					}
					String path = tpath +"-"+ getUser().getId();
					Integer selectDepth = depth + flowreport.getProfitnum();
					List<Object[]> countList = usersDAO.findCodeByUserPath(null,path,selectDepth);
					List<Object[]> ooList = new ArrayList<Object[]>();
					for(Object[] o : countList){
						usercode = (String)o[1];
						if(usercode != null && !"".equals(usercode)){								
							flow.setUsercode(usercode);
							List<Object[]> tempo = businessflowDAO.partFlowByUser(flow);
							ooList.addAll(tempo);
						}else{
							continue;
						}
					}
					return totalReport(ooList, username,flow);
				}else{					
					if(usercode != null && !"".equals(usercode)){
						flow.setUsercode(usercode);
						List<Object[]> objects = businessflowDAO.partFlowByUser(flow);
						return totalReport(objects, username,flow);
					}
				}
			}
		}
		List<Object[]> objects = businessflowDAO.partFlowByUser(flow);
		return totalReport(objects, username,flow);
	}
	
	/**
	 * 提取分润处理公共方法(服务编码分类.待确定区分服务编码还是其他字段,暂以paydesc为区分)
	 */
	public List<FlowReport> totalReport(List<Object[]> objects,String username,Businessflow flow){
		List<FlowReport> frList = new ArrayList<FlowReport>();
		String servicetype1 = ConfigInfo.getValue("SERVICECODE1");
		String servicetype2 = ConfigInfo.getValue("SERVICECODE2");
		float totalMoney1=0;
		float totalMoney2=0;
		float totalMoney3=0;
		int n1=0;//num
		int n2=0;
		int n3=0;
		for(Object[] o : objects){
			Float a = (Float)o[3];//money
			Float b = (Float)o[4];//fee
			String paydesc = (String)o[5];
			if(paydesc != null){					
				if(servicetype1.equals(paydesc.trim())){
					//1%分析，先求和
					totalMoney1+= a;
					n1++;
//					业务调整，不再使用手续费//根据不同类型统计分润笔数
//					if(b >= 35){
//						n1++;
//					}
				}else if(servicetype2.equals(paydesc.trim())){
					//0.49%
					totalMoney2+=a;
					n2++;
				}else{
					totalMoney3+=a;
					n3++;
				}
			}
		}
		if(objects.size() != 0){			
			String starttime = (String)objects.get(objects.size()-1)[1];
			String endtime = (String)objects.get(0)[1];
			if(n1 > 0){			
				FlowReport fr1 = new FlowReport();//1%
				fr1.setUsername(username);
				fr1.setServicename((String)objects.get(0)[0]+"("+servicetype1+")");
				fr1.setPaymoney(totalMoney1);
				fr1.setProfitnum(n1);
				fr1.setProfitmoney((float) (totalMoney1*0.0001));//总额*0.0001
				fr1.setStartdate(flow.getPaydate());
				fr1.setEnddate(flow.getPaytime());
				fr1.setRealstartdate(starttime);
				fr1.setRealenddate(endtime);
				frList.add(fr1);
			}
			if(n2 >0){			
				FlowReport fr2 = new FlowReport();//0.49%
				fr2.setUsername(username);
				fr2.setServicename((String)objects.get(0)[0]+"("+servicetype1+")");
				fr2.setPaymoney(totalMoney2);
				fr2.setProfitnum(n2);
				fr2.setProfitmoney((float) (totalMoney2*0.0002));
				fr2.setStartdate(flow.getPaydate());
				fr2.setEnddate(flow.getPaytime());
				fr2.setRealstartdate(starttime);
				fr2.setRealenddate(endtime);
				frList.add(fr2);
			}
			if(n3 > 0){				
				FlowReport fr3 = new FlowReport();//其他
				fr3.setUsername(username);
				fr3.setServicename((String)objects.get(0)[0]+"(其他)");
				fr3.setPaymoney(totalMoney3);
				fr3.setProfitnum(n3);
				fr3.setProfitmoney((float)0);
				fr3.setStartdate(flow.getPaydate());
				fr3.setEnddate(flow.getPaytime());
				fr3.setRealstartdate(starttime);
				fr3.setRealenddate(endtime);
				frList.add(fr3);
			}
		}
		FlowReport fr4 = new FlowReport();//total
		fr4.setServicename("小计：");
		fr4.setPaymoney(totalMoney1+totalMoney2+totalMoney3);
		fr4.setProfitnum(n1+n2+n3);
		fr4.setProfitmoney((float) (n1*0.13+n2*0.0002));
		frList.add(fr4);
		return frList;
	} 
	
	/**
	 * 分润求和公共方法，主要用在定时结算中，返回时间段内的分润和(服务编码分类.待确定区分服务编码还是其他字段,暂以paydesc为区分，其他不计入分润)
	 * @author 张恩备
	 * @date 2016-4-22 下午06:35:48
	 */
	public Float profitTotal(List<Object[]> objects){
		String servicetype1 = ConfigInfo.getValue("SERVICECODE1");
		String servicetype2 = ConfigInfo.getValue("SERVICECODE2");
		float totalMoney1=0;
		float totalMoney2=0;
		float totalMoney3=0;
		int n1=0;//num
		int n2=0;
		int n3=0;
		for(Object[] o : objects){
			Float a = (Float)o[3];//money
			Float b = (Float)o[4];//fee
			String paydesc = (String)o[5];
			if(paydesc != null){					
				if(servicetype1.equals(paydesc.trim())){
					//1%分析，先求和
					totalMoney1+= a;
					//根据不同类型统计分润笔数
					if(b >= 35){
						n1++;
					}
				}else if(servicetype2.equals(paydesc.trim())){
					//0.49%
					totalMoney2+=a;
					n2++;
				}else{
					totalMoney3+=a;
					n3++;
				}
			}
		}
		float total = (float) (n1*0.13+n2*0.0002);
		return total;
	}
}
