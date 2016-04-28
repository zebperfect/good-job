package com.zebsoft.right.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.right.domain.Menu;
import com.zebsoft.right.domain.UserCount;
import com.zebsoft.right.domain.Users;
import com.zebsoft.right.service.UsersService;
import com.zebsoft.zzz.util.ConfigInfo;
import com.zebsoft.zzz.util.LogHelper;

/**
 * 类说明：用户管理类
 * @author zeb
 * @since 2014年3月11日21:02:39
 * @version V1.0
 */
@Controller("usersAction")
@Scope("prototype")
public class UsersAction extends ActionSupport {
	private static final Logger log = LoggerFactory.getLogger(UsersAction.class);	
	/*****************类方法（↓）********************/	
	/**
	 * 分页显示所有用户
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String queryUsers() throws Exception{
		try {
			this.success = false;
			Map<String, Object> map = usersService.queryUsers(start, limit);
			items_Users = (List<Users>) map.get("items");
			totalProperty = (Long) map.get("count");
			this.success = true;
		} catch (Exception e) {
			log.error("queryUsers Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加用户
	 * @return
	 * @throws Exception 
	 */
	public String addUsers() throws Exception{
		try {
			this.success = false;
			if (usersService.addUsers(user)) {
				this.success = true;
				log.info(LogHelper.userLog("添加了一个用户名为"+user.getUsername()+"的用户"));
			} else{
				this.success = false;
			}
		} catch (Exception e) {
			log.error("addUsers Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 第一步，用户注册
	 * @return
	 * @throws Exception 
	 */
	public String registerUser() throws Exception{
		try {
			//delData存验证码，path存短信验证码
			this.success = false;
			String random = (String) session.get("random");
			String smscode = (String) session.get("smscode");
			if(delData == null || path ==null || !random.equals(delData) || !smscode.equals(path)
					|| user == null || !newPassword.equals(user.getPassword())){
				return INPUT;
			}
			if (usersService.addUsers(user)) {
				this.success = true;
				session.put("tempuser", user);
				session.put("singlemoney", ConfigInfo.getValue("MONEY"));
				log.info("添加了一个用户名为"+user.getUsername()+"的用户");
				return SUCCESS;
			} else{
				this.success = false;
				return INPUT;
			}
		} catch (Exception e) {
			log.error("registerUser Exception:"+e.getMessage(),e);
			return ERROR;
		}
	}
	
	/**
	 * 第二步，填银行待支付
	 * @author 张恩备
	 * @date 2016-4-14 下午02:45:59
	 */
	public String paymoney() throws Exception{
		try {
			if(user == null || user.getId() == null){
				return "failed";
			}
			if(usersService.updateUsers(user)){
				log.info("----用户："+user.getUsername()+"进入支付页面，等待支付----");
				//调支付连接
				return SUCCESS;
			}else{
				return "failed";
			}
		} catch (Exception e) {
			log.error("paymoney Exception:"+e.getMessage(),e);
			return ERROR;
		}
	}
	
	/**
	 * 第三步，支付反馈结果
	 * @author 张恩备
	 * @date 2016-4-14 下午03:03:42
	 */
	public String payresult() {
		log.info("-----receive pay result-----");
		try {
			if(user != null && user.getUsable() != null){
				Users u =(Users)session.get("tempuser");
				if(usersService.updateResult(u,user.getUsable())){
					return SUCCESS;
				}else{				
					return "failed";
				}
			}else{
				return "failed";
			}
		} catch (Exception e) {
			log.error("payresult Exception:"+e.getMessage(),e);
			return "failed";
		}
	}
	/**
	 * 删除用户（支持多条删除）
	 * @return
	 * @throws Exception 
	 */
	public String delUsers() throws Exception{
		try {
			this.success = false;
			if(usersService.delUsers(delData)){
				this.success = true;
				 log.info(LogHelper.userLog("删除了用户id为"+delData+"的用户"));
			}
			else{
				this.success = false;
			}
		} catch (Exception e) {
			log.error("delUsers Exception:"+e.getMessage(),e);
		}
		return SUCCESS;	
	}
	
	/**
	 * 修改用户信息
	 * @return
	 * @throws Exception 
	 */
	public String updateUsers() throws Exception{
		try {
			this.success = false;
			if(usersService.updateUsers(user)){
				this.success = true;
				log.info(LogHelper.userLog("修改了用户名为"+user.getUsername()+"的基本信息"));
			}
			else{
				this.success = false;
			}
		} catch (Exception e) {
			log.error("updateUsers Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户修改自己信息
	 * @return
	 * @throws Exception 
	 */
	public String updateSelfUsers() throws Exception{
		try {
			if(usersService.updateSelfUsers(user)){
				log.info("修改了用户名为"+user.getUsername()+"的基本信息");
				return SUCCESS;
			}else{
				return INPUT;
			}
		} catch (Exception e) {
			log.error("updateSelfUsers Exception:"+e.getMessage(),e);
			return ERROR;
		}
	}
	/**
	 * 绑定用户编码信息
	 * @return
	 * @throws Exception 
	 */
	public String updateUserCode() throws Exception{
		try {
			this.success = false;
			if(usersService.updateUserCode(user)){
				this.success = true;
				log.info(LogHelper.userLog("修改了用户名为"+user.getUsername()+"的基本信息"));
			}
			else{
				this.success = false;
			}
		} catch (Exception e) {
			log.error("updateUserCode Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 条件查询用户
	 * @return  
	 * @throws Exception 
	 */
	public String selectUsers() throws Exception{
		try {
			this.success = false;
			Map<String, Object> map = usersService.selectUsers(start, limit, user);
				items_Users = (List<Users>) map.get("items");
				totalProperty = (Long) map.get("count");
				this.success = true;
		} catch (Exception e) {
			log.error("selectUsers Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据用户Id找到用户对象
	 * @return
	 * @throws Exception 
	 */
	public String findUsersById() throws Exception{
		try {
			this.success = false;
			items_Users=new ArrayList<Users>();
			items_Users.add(usersService.findUsersById(user.getId()));
			if (items_Users.size() ==1) {
				this.success = true;
			} else
				this.success = false;
		} catch (Exception e) {
			log.error("findUsersById Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据用户手机号查找是否存在
	 * @return
	 * @throws Exception 
	 */
	public String findUsersByTelephone() throws Exception{
		try {
			this.success = false;
			Boolean bb = usersService.findUsersByTelephone(user.getTelephone());
			if (bb) {
				this.success = true;
			} else
				this.success = false;
		} catch (Exception e) {
			log.error("findUsersByTelephone Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 判断验证码是否正确
	 * @author 张恩备
	 * @throws Exception 
	 * @date 2016-4-13 下午03:26:24
	 */
	public String verifyCode() throws Exception{
		try {
			success = false;
			String random = (String) session.get("random");
			if(delData == null){
				return SUCCESS;
			}
			if(random.trim().equals(delData.trim())){
				success = true;
				return SUCCESS;
			}
		} catch (Exception e) {
			log.error("verifyCode Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 判断短信验证码是否正确
	 * @author 张恩备
	 * @throws Exception 
	 * @date 2016-4-13 下午03:26:24
	 */
	public String smsCode() throws Exception{
		try {
			success = false;
			String smscode = (String) session.get("smscode");
			if(delData == null){
				return SUCCESS;
			}
			if(smscode.trim().equals(delData.trim())){
				success = true;
				return SUCCESS;
			}
		} catch (Exception e) {
			log.error("smsCode Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户登录操作
	 * @return
	 * @throws Exception 
	 */
	public String login() throws Exception{
		try {
			success = false;
			String random = (String) session.get("random");
			//用户名暂存验证码
			if(user == null || user.getUsername() == null){
				return SUCCESS;
			}
			if(random.trim().equals(user.getUsername().trim())){				
				List<Users> userList = usersService.login(user);
				if (userList.size() == 1) {
					Users userTemp = userList.get(0);
					if(userTemp.getUsable() == 0){
						session.put("tempuser", userTemp);
						session.put("singlemoney", ConfigInfo.getValue("MONEY"));
						start=1;
						success = true;
						log.info("未激活用户登录：" + userTemp.getTelephone());
					}else{						
						//用户可以看到的菜单放到seeion里  为了拦截url方面
						List<Menu> menuTempList = new ArrayList<Menu>();
						menuTempList = usersService.queryUrl(userTemp);
						session.put("user", userTemp);
						session.put("menu", menuTempList);
						success = true;
						log.info(LogHelper.userLog("用户登录：" + userTemp.getTelephone()));
					}
				}
				else{
					success = false;
				}
			}
		} catch (Exception e) {
			log.error("login Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户登出操作
	 * @return
	 */
	public String loginOut(){
		try {
			Users rightUser = (Users)session.get("user");
			log.info(LogHelper.userLog("用户登出:" + rightUser.getTelephone()));
			session.clear();
			success = true;
		} catch (RuntimeException e) {
			success = false;
			log.error("loginOut Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户重置密码
	 * @return
	 */
	public String resetPassword() {
		try {
			Users rightUser = (Users)session.get("user");
			usersService.resetPassword(user);
			success = true;
			log.info(LogHelper.userLog("用户名为"+rightUser.getUsername()+"重置了密码"));
		} catch (RuntimeException e) {
			success = false;
			log.error("resetPassword Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户修改密码
	 * @return
	 */
	public String updatePassword(){
		try {
			Users rightUser = (Users)session.get("user");
			 if(usersService.updatePassword(newPassword, oldPassword,rightUser)){
				 this.success = true;
				 log.info(LogHelper.userLog("用户名为"+rightUser.getUsername()+"修改了密码，将老密码"+oldPassword+"修改成了"+newPassword));
			 }
			 else
				 this.success = false;
		} catch (RuntimeException e) {
			success = false;
			log.error("updatePassword Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询当前用户的各级用户量
	 * @return  
	 * @throws Exception 
	 */
	public String selectUserCount() throws Exception{
		try {
			this.success = false;
			items_UserCount= usersService.selectUserCount(start, limit, user);
			totalProperty = (long) items_UserCount.size();
			this.success = true;
		} catch (Exception e) {
			this.success = false;
			log.error("selectUserCount Exception:"+e.getMessage(),e);
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
			path= usersService.exportExcel(user);
			this.success = true;
		} catch (Exception e) {
			this.success = false;
			log.error("exportExcel Exception:"+e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	/*****************类方法（↑）********************/
	
	/*****************类属性（↓）********************/

	private Map session = ActionContext.getContext().getSession();
	private Integer start;
	private Integer limit;
	private String delData;
	private boolean success;
	private Users user;
	private Long totalProperty;
	private List<Users> items_Users;
	private String oldPassword;
	private String newPassword;
	@Resource
	private UsersService usersService;
	
	private List<UserCount> items_UserCount;
	private String path;
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
	

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Long getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(Long totalProperty) {
		this.totalProperty = totalProperty;
	}

	public List<Users> getItems_Users() {
		return items_Users;
	}

	public void setItems_Users(List<Users> itemsUsers) {
		items_Users = itemsUsers;
	}

	public List<UserCount> getItems_UserCount() {
		return items_UserCount;
	}

	public void setItems_UserCount(List<UserCount> items_UserCount) {
		this.items_UserCount = items_UserCount;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/*****************类getset方法（↑）********************/	
	
}
