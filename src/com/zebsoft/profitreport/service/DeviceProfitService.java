package com.zebsoft.profitreport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.zebsoft.profitreport.action.DeviceProfitAction;
import com.zebsoft.profitreport.domain.UserDTO;
import com.zebsoft.right.DAO.UsersDAO;
import com.zebsoft.right.domain.Users;
import com.zebsoft.zzz.util.ConfigInfo;
/**
 * 设备提成业务功能
 * @author 张恩备
 * @date 2016-3-24 下午03:47:47
 */
@Service("deviceProfitService")
public class DeviceProfitService {

	private static final Logger log = LoggerFactory.getLogger(DeviceProfitService.class);	
	@Resource
	private UsersDAO usersDAO;
	
	private static Integer profitdepth= Integer.valueOf(ConfigInfo.getValue("PROFITDEPTH"));
	private static Integer profit1= Integer.valueOf(ConfigInfo.getValue("PROFIT1"));
	private static Integer profit2= Integer.valueOf(ConfigInfo.getValue("PROFIT2"));
	private static Integer profit3= Integer.valueOf(ConfigInfo.getValue("PROFIT3"));
	private static Integer profit4= Integer.valueOf(ConfigInfo.getValue("PROFIT4"));
	private static Integer profit5= Integer.valueOf(ConfigInfo.getValue("PROFIT5"));
	private static Integer profit6= Integer.valueOf(ConfigInfo.getValue("PROFIT6"));
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
	 * 查询当前用户的设备提成
	 * @return
	 */
	public List<UserDTO> selectProfitCount(Integer start, Integer limit, Users user){
		List<UserDTO> userdto = new ArrayList<UserDTO>();
		//定义一个map来存放每个元素出现的次数
        Map<Integer,Integer> elementsCount=new HashMap<Integer,Integer>();
		//得到所有含有当前用户Path的可用已激活的账户
        String telephone ="";
        String username = "";
		if(user != null && (user.getTelephone() != null && !"".equals(user.getTelephone()))){
			telephone = user.getTelephone();
		}else{
			telephone = getUser().getTelephone();
		}
		
		List<Users> userList = usersDAO.findByTelephone(telephone);
		if(userList.size() > 0){
			Integer depth = userList.get(0).getDepth();
			String tpath = userList.get(0).getPath();
			Integer userid = userList.get(0).getId();
			username = userList.get(0).getUsername();
			if(username == null || "".equals(username) || tpath == null || depth == null || userid == null){
				return userdto;
			}
			//只下找到多少级
			String path = tpath +"-"+userid ;
			Integer downdepth = depth+profitdepth;
			List<Object[]> countList = usersDAO.findDepthByUserPath(user,path,downdepth);
			for(Object[] o : countList){
				//二级(Integer)o[0] - depth+1
				Integer pathDepth =(Integer)o[0] - depth+1;
				if(pathDepth != 1){					
					Integer i=elementsCount.get(pathDepth);
					if(i==null){
						elementsCount.put(pathDepth, 1);
					}else{
						elementsCount.put(pathDepth, i+1);
					}
				}
			}
			//输出结果
			int count =0;
			int moneycount = 0;
			int profit=0;
	        for(Integer key:elementsCount.keySet()){
//	            System.out.println(key+"出现了 " +elementsCount.get(key) +"次");
	        	if(key == 2){
	        		profit = profit1;
	        	}else if(key == 3){
	        		profit = profit2;	        		
	        	}else if(key == 4){
	        		profit = profit3;	        		
	        	}else if(key == 5){
	        		profit = profit4;	        		
	        	}else if(key == 6){
	        		profit = profit5;	        		
	        	}else if(key == 7){
	        		profit = profit6;	        		
	        	}
	            UserDTO ud = new UserDTO();
	            int money = profit*elementsCount.get(key);
	            ud.setUsername(username);
	            ud.setUserdepth(key+"级");
	            ud.setUsercount(String.valueOf(elementsCount.get(key))+"人");
	            ud.setProfitfee(String.valueOf(profit));
	            ud.setProfitcount(String.valueOf(money));
	            userdto.add(ud);
	            count+=elementsCount.get(key);
	            moneycount+=(money);
	        }
	        UserDTO dd = new UserDTO();
            dd.setUserdepth("人数总计");
            dd.setUsercount(String.valueOf(count)+"人");
            dd.setProfitfee("提成总额");
            dd.setProfitcount(String.valueOf(moneycount));
            userdto.add(dd);
            return userdto;
		}
		return userdto;
	}
	
	/**
	 * 一段时间内用户设备提成总和
	 * @return
	 */
	public Float deviceProfitTotal(Users user){
		//定义一个map来存放每个元素出现的次数
        Map<Integer,Integer> elementsCount=new HashMap<Integer,Integer>();
		//得到所有含有当前用户Path的可用已激活的账户
		if(user != null){
			String telephone =user.getTelephone();
			String username = user.getUsername();
			Integer depth = user.getDepth();
			String tpath = user.getPath();
			Integer userid = user.getId();
			if(telephone == null || "".equals(telephone) || username == null || "".equals(username) 
					|| tpath == null || depth == null || userid == null){
				log.info("--用户信息不完整----");
				return null;
			}
			//暂不开始时间和结束时间默认处理
			String starttime = user.getUsercard();
			String endtime = user.getBankcard();
			if(starttime == null || "".equals(starttime) || endtime == null || "".equals(endtime)){
				return null;
			}
			//只下找到多少级
			String path = tpath +"-"+userid ;
			Integer downdepth = depth+profitdepth;
			List<Object[]> countList = usersDAO.findDepthByUserPath(user,path,downdepth);
			for(Object[] o : countList){
				//二级(Integer)o[0] - depth+1
				Integer pathDepth =(Integer)o[0] - depth+1;
				if(pathDepth != 1){					
					Integer i=elementsCount.get(pathDepth);
					if(i==null){
						elementsCount.put(pathDepth, 1);
					}else{
						elementsCount.put(pathDepth, i+1);
					}
				}
			}
			//输出结果
			int count =0;
			int moneycount = 0;
			int profit=0;
	        for(Integer key:elementsCount.keySet()){
//	            System.out.println(key+"出现了 " +elementsCount.get(key) +"次");
	        	if(key == 2){
	        		profit = profit1;
	        	}else if(key == 3){
	        		profit = profit2;	        		
	        	}else if(key == 4){
	        		profit = profit3;	        		
	        	}else if(key == 5){
	        		profit = profit4;	        		
	        	}else if(key == 6){
	        		profit = profit5;	        		
	        	}else if(key == 7){
	        		profit = profit6;	        		
	        	}
	            int money = profit*elementsCount.get(key);
	            count+=elementsCount.get(key);
	            moneycount+=(money);
	        }
            return (float) moneycount;
		}
		return null;
	}
}
