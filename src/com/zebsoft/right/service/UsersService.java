package com.zebsoft.right.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.zebsoft.right.DAO.MenuDAO;
import com.zebsoft.right.DAO.RoleDAO;
import com.zebsoft.right.DAO.RolemenuDAO;
import com.zebsoft.right.DAO.RoleusersDAO;
import com.zebsoft.right.DAO.UsersDAO;
import com.zebsoft.right.domain.Menu;
import com.zebsoft.right.domain.Role;
import com.zebsoft.right.domain.Rolemenu;
import com.zebsoft.right.domain.Roleusers;
import com.zebsoft.right.domain.UserCount;
import com.zebsoft.right.domain.Users;
import com.zebsoft.zzz.util.CommonsMethod;
import com.zebsoft.zzz.util.ConfigInfo;
import com.zebsoft.zzz.util.Md5;
import com.zebsoft.zzz.util.MyUtils;
import com.zebsoft.zzz.util.SystemState;


/**
 * 类说明  用户管理类
 * @author zeb
 * @since 2014-3-12 10:15:10
 * @version 1.0
 */
@Service("usersService")
public class UsersService {

	private static final Logger log = LoggerFactory.getLogger(UsersService.class);	
	private static Integer userdepth= Integer.valueOf(ConfigInfo.getValue("USERDEPTH"));
	private static Integer money= Integer.valueOf(ConfigInfo.getValue("MONEY"));
	private static Integer shiptypeout= Integer.valueOf(ConfigInfo.getValue("SHIPTYPEOUT"));
	private static Integer shiptypein= Integer.valueOf(ConfigInfo.getValue("SHIPTYPEIN"));
	private static Integer shiptypeself= Integer.valueOf(ConfigInfo.getValue("SHIPTYPESELF"));
	@Resource
	private UsersDAO usersDAO;
	@Resource
	private RolemenuDAO rolemenuDAO;
	@Resource
	private RoleusersDAO roleusersDAO;
	@Resource
	private RoleDAO roleDAO;
	@Resource
	private MenuDAO menuDAO;
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
	 * 添加用户
	 * @return
	 */
	public boolean addUsers(Users user){
		if(user == null){
			return false;
		}
		List<Users> usersList = usersDAO.findByTelephone(user.getTelephone());
		if(usersList.size() == 0){
			if(user.getParentiphone() != null && !"".equals(user.getParentiphone())){
				List<Users> tempList = usersDAO.findByTelephone(user.getParentiphone());
				if(tempList.size() != 0){					
					log.info("注册用户: "+user.getTelephone()+" 推荐人: "+user.getParentiphone());
					user.setParentid(tempList.get(0).getId());
					user.setDepth(tempList.get(0).getDepth()+1);
					user.setPath(tempList.get(0).getPath()+"-"+tempList.get(0).getId());
				}else{
					return false;
				}
			}else{
				log.info("注册用户 "+user.getTelephone()+" 无推荐人");
				//无推荐人
				user.setParentid(1);
				user.setParentiphone("13888888888");//待修改
				user.setDepth(2);
				user.setPath("0-1");
			}
			user.setUsable(SystemState.NOUSABLE);
			//记录激活时间
			user.setCreatetime(CommonsMethod.getNowCorrect2Second());
			user.setState(SystemState.USE);
			Md5 md5 = new Md5();
			user.setPassword(md5.getMD5Str(user.getPassword()));
			usersDAO.save(user);
			//注册赋予普通用户角色
			Roleusers ru = new Roleusers();
			Role role = new Role();
			role.setId("2");
			ru.setRoleId(role);
			ru.setUsersId(user);
			ru.setState(SystemState.USE);
			roleusersDAO.save(ru);
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * 分页显示所有用户
	 * 
	 * @return
	 */
	public Map<String, Object> queryUsers(Integer start, Integer limit){
		return usersDAO.queryUsers(start,limit);
	}
	
	/**
	 *  删除用户（支持多条删除）
	 * @param delData
	 * @return
	 * @throws Exception 
	 */
	public boolean delUsers(String delData) throws Exception{
		 String userName = usersDAO.delUsers(delData);
		 return true;
	}
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public boolean updateUsers(Users user){
		if(user != null && user.getId() !=null){			
			Users userTemp = usersDAO.findByUserId(user.getId());
			if(userTemp!=null){
				if(user.getParentiphone() != null && !"".equals(user.getParentiphone())){					
					if(user.getParentiphone() != userTemp.getParentiphone() && !user.getParentiphone().equals(userTemp.getParentiphone())){
						List<Users> usersList = usersDAO.findByTelephone(user.getParentiphone());
						if(usersList.size() != 0 && usersList.get(0).getId() != userTemp.getParentid()){
							userTemp.setParentiphone(user.getParentiphone());
							userTemp.setParentid(usersList.get(0).getId());
							userTemp.setDepth(usersList.get(0).getDepth()+1);
							userTemp.setPath(usersList.get(0).getPath()+"-"+usersList.get(0).getId());
						}else{
							return false;
						}
					}
				}
				//姓名，身份证号不允许修改
				if(user.getUsercode() != null && !"".equals(user.getUsercode())){
					userTemp.setUsercode(user.getUsercode());
					userTemp.setUsable(SystemState.CHECKED);
				}
				if(user.getBankname() != null && !"".equals(user.getBankname())){					
					userTemp.setBankname(user.getBankname());
				}
				if(user.getShipaddress() != null && !"".equals(user.getShipaddress())){
					userTemp.setShipaddress(user.getShipaddress());
				}
				if(user.getBankcard() != null && !"".equals(user.getBankcard())){
					userTemp.setBankcard(user.getBankcard());
				}
				if(user.getShiptype() != null && !"".equals(user.getShiptype())){
					userTemp.setShiptype(user.getShiptype());
					if(Integer.valueOf(user.getShiptype()) == 0){
						userTemp.setPaymoney(String.valueOf(money+shiptypeout));
					}else if(Integer.valueOf(user.getShiptype()) == 1){
						userTemp.setPaymoney(String.valueOf(money+shiptypein));
					}else if(Integer.valueOf(user.getShiptype()) == 2){
						userTemp.setPaymoney(String.valueOf(money+shiptypeself));
					}else{
						userTemp.setPaymoney(String.valueOf(money+shiptypeout));						
					}
				}
				if(user.getBankaddress() != null && !"".equals(user.getBankaddress())){
					userTemp.setBankaddress(user.getBankaddress());
				}
				if(user.getUserdesc() != null && !"".equals(user.getUserdesc())){
					userTemp.setUserdesc(user.getUserdesc());
				}
				usersDAO.update(userTemp);
				return true;
			}
		}
		return false;
	}
	/**
	 * 更新支付结果
	 * @author 张恩备
	 * @date 2016-4-17 上午10:40:28
	 */
	public boolean updateResult(Users user,Integer usable){
		Users u = usersDAO.findByUserId(user.getId());
		if(u != null){
			if(usable.equals("1")){
				u.setCreatetime(CommonsMethod.getNowCorrect2Second());
			}
			u.setUsable(usable);
			usersDAO.update(u);
			return true;
		}else{			
			return false;
		}
	}
	/**
	 * 用户修改自身信息
	 * @param user
	 * @return
	 */
	public boolean updateSelfUsers(Users user){
		if(user != null && user.getId() !=null){			
			Users userTemp = usersDAO.findByUserId(user.getId());
			Integer id = getUser().getId();
			if(!userTemp.getId().equals(id)){
				log.info("--当前修改的不是自身用户----");
				return false;
			}
			if(userTemp!=null){
//				if(user.getParentiphone() != null && !"".equals(user.getParentiphone())){					
//					if(user.getParentiphone() != userTemp.getParentiphone() && !user.getParentiphone().equals(userTemp.getParentiphone())){
//						List<Users> usersList = usersDAO.findByTelephone(user.getParentiphone());
//						if(usersList.size() != 0 && usersList.get(0).getId() != userTemp.getParentid()){
//							userTemp.setParentiphone(user.getParentiphone());
//							userTemp.setParentid(usersList.get(0).getId());
//							userTemp.setDepth(usersList.get(0).getDepth()+1);
//							userTemp.setPath(usersList.get(0).getPath()+"-"+usersList.get(0).getId());
//						}else{
//							return false;
//						}
//					}
//				}
				//姓名，身份证号不允许修改
				if(user.getBankname() != null && !"".equals(user.getBankname())){					
					userTemp.setBankname(user.getBankname());
				}
				if(user.getShipaddress() != null && !"".equals(user.getShipaddress())){
					userTemp.setShipaddress(user.getShipaddress());
				}
				if(user.getBankcard() != null && !"".equals(user.getBankcard())){
					userTemp.setBankcard(user.getBankcard());
				}
				if(user.getBankaddress() != null && !"".equals(user.getBankaddress())){
					userTemp.setBankaddress(user.getBankaddress());
				}
				if(user.getShiptype() != null && !"".equals(user.getShiptype())){
					if(Integer.valueOf(user.getShiptype()) == 0){
						user.setPaymoney(String.valueOf(money+shiptypeout));
					}else if(Integer.valueOf(user.getShiptype()) == 1){
						user.setPaymoney(String.valueOf(money+shiptypein));
					}else if(Integer.valueOf(user.getShiptype()) == 2){
						user.setPaymoney(String.valueOf(money+shiptypeself));
					}else{
						user.setPaymoney(String.valueOf(money+shiptypeout));						
					}
				}
				if(user.getUserdesc() != null && !"".equals(user.getUserdesc())){
					userTemp.setUserdesc(user.getUserdesc());
				}
				usersDAO.update(userTemp);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 绑定设备状态信息
	 * @param user
	 * @return
	 */
	public boolean updateUserCode(Users user){
		if(user != null && user.getId() !=null){			
			Users userTemp = usersDAO.findByUserId(user.getId());
			if(userTemp!=null){
				if(user.getUsercode() != null && !"".equals(user.getUsercode()) 
						&& !user.getUsercode().equals(userTemp.getUsercode())){
					userTemp.setUsercode(user.getUsercode());
					userTemp.setUsable(SystemState.CHECKED);
				} else{					
					userTemp.setUsable(user.getUsable());
				}
				if(user.getUserdesc() != null && !"".equals(user.getUserdesc())){
					userTemp.setUserdesc(user.getUserdesc());
				}
				usersDAO.update(userTemp);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据用户Id找到用户对象
	 * @return 
	 */
	public Users findUsersById(Integer usersId){
		return usersDAO.findByUserId(usersId);
	}
	
	/**
	 * 查找手机号是否存在
	 * @return 
	 */
	public Boolean findUsersByTelephone(String telephone){
		List<Users> userList = usersDAO.findByTelephone(telephone);
		if(userList.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 条件查询用户
	 * @return
	 */
	public Map<String , Object> selectUsers(Integer start, Integer limit, Users user){
		return usersDAO.selectUsers(start,limit,user);
	}
	
	/**
	 * 用户登录操作
	 * @return
	 */
	public List<Users> login(Users user){
		Md5 md5 =new Md5();
		user.setPassword(md5.getMD5Str(user.getPassword()));
		return usersDAO.login(user);
	} 
	
	/**
	 * 根据用户显示出用户所拥有的所有权限
	 * @param user
	 * @return
	 */
	public List<Menu> queryUrl(Users user){
		//通过用户在Roleusers表中找到所有与该用户用对应的条
		List<Roleusers> roleusersListTemp = roleusersDAO.findRoleByUser(String.valueOf(user.getId()));
		List<Role> roleListTemp = new ArrayList<Role>();
		List<Menu> menuTemp = new ArrayList<Menu>();
		//找到该用户所对应的所有角色List
		for (Roleusers roleusers : roleusersListTemp) {
			Role roleTemp = roleDAO.findRoleById(roleusers.getRoleId().getId());
			roleListTemp.add(roleTemp);
		}
		for (Role role : roleListTemp) {
			List<Rolemenu> rolemenuListTemp = rolemenuDAO.findMenuByRole(role.getId());
			for (Rolemenu rolemenu : rolemenuListTemp) {
				Menu menu = menuDAO.findMenuById(rolemenu.getMenuId().getId());
				menuTemp.add(menu);
			}
		}
		return menuTemp;
	}
	
	/**
	 * 用户重置密码
	 * @return
	 */
	public void resetPassword(Users user){
		try {
			Users userTemp = usersDAO.findByUserId(user.getId());
			Md5 md5 =new Md5();
			userTemp.setPassword(md5.getMD5Str("123456"));
			usersDAO.save(userTemp);
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
		
	}
	
	/**
	 * 用户修改密码
	 * @return
	 */
	public boolean updatePassword(String newPassword,String oldPassword,Users user){
		Md5 md5 =new Md5();
		if(!md5.checkpassword(oldPassword, user.getPassword())){
			return false;
		}else{
			user.setPassword(md5.getMD5Str(newPassword));
			usersDAO.update(user);
			return true;
		}
	}
	
	/**
	 * 查询当前用户的各级用户量
	 * @return
	 */
	public List<UserCount> selectUserCount(Integer start, Integer limit, Users user){
		List<UserCount> usercount = new ArrayList<UserCount>();
		//定义一个map来存放每个元素出现的次数
        Map<Integer,Integer> elementsCount=new HashMap<Integer,Integer>();
		//得到所有含有当前用户Path的可用已激活的账户
        String telephone ="";
        if(user != null && (user.getTelephone() != null && !"".equals(user.getTelephone()))){
			telephone = user.getTelephone();
		}else{
			telephone = getUser().getTelephone();
		}
		List<Users>userList = usersDAO.findByTelephone(telephone);
		if(userList.size() > 0){
			Integer depth = userList.get(0).getDepth();
			String tpath = userList.get(0).getPath();
			Integer userid = userList.get(0).getId();
			if(depth ==null || tpath == null || userid == null){
				return usercount;
			}
			//只下找到六级
			String path = tpath +"-"+userid ;
			Integer udepth = depth + userdepth;
			List<Object[]> countList = usersDAO.findDepthByUserPath(user,path,udepth);
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
	        for(Integer key:elementsCount.keySet()){
//	            System.out.println(key+"出现了 " +elementsCount.get(key) +"次");
	            UserCount uc = new UserCount();
	            uc.setUserDepth(key+"级");
	            uc.setUsercount(String.valueOf(elementsCount.get(key))+"人");
	            usercount.add(uc);
	            count+=elementsCount.get(key);
	        }
	        UserCount cc = new UserCount();
            cc.setUserDepth("总计");
            cc.setUsercount(String.valueOf(count)+"人");
            usercount.add(cc);
            return usercount;
		}
		return usercount;
	}
	
	/**
	 * 导出excel数据
	 * @return
	 */
	public String exportExcel(Users user){
		List<Object[]> ObjItems = new ArrayList<Object[]>();
		Object[] ob = { "手机号", "客户编码", "推荐人手机号", "姓名", "身份证号", "开户银行卡号", "开户银行", "开户地址","注册时间"};
		ObjItems.add(ob);
		
		List<Object[]> tempList = usersDAO.getExcelData(user);
		ObjItems.addAll(tempList);
		String excelName = CommonsMethod.getNowCorrect2Second()+ ".xls";
		String rootPath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
		String filePath = "tempExcel/" + excelName;
		MyUtils.mkDirectory(rootPath + "tempExcel/");
		MyUtils.putExcel(rootPath, filePath, "", ObjItems);
		return filePath;
	}
}
