package com.zebsoft.right.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zebsoft.right.domain.Users;
import com.zebsoft.zzz.util.BaseHibernateDAO;
import com.zebsoft.zzz.util.SystemState;
/**
 * 说明：用户数据库操作
 * @author Thinker
 * since 2014年3月11日21:16:55
 * version 1.0
 */

@Repository("usersDAO")
public class UsersDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(UsersDAO.class);

	
	/**********************自定义方法（↓）****************************/
	/**
	 * 通过客户编号查找UsersList，有则返回List.size()大于0
	 * @author zeb
	 * @since 2014年3月11日21:20:00
	 * @param username
	 * @return
	 */
	public List<Users> findByUserCode(String usercode){
		try{
		String queryString = "from Users as u where u.state="+SystemState.USE+" and " +
				"u.usercode=:usercode";
		Query query = getSession().createQuery(queryString);
		query.setString("usercode", usercode);
		return query.list();
	} catch (HibernateException e) {
		// TODO Auto-generated catch block
		throw e;
		}
	}
	
	/**
	 * 通过手机号查找UsersList，有则返回List.size()大于0
	 * @author zeb
	 * @since 2014年3月11日21:20:09
	 * @param truename
	 * @return
	 */
	public List<Users> findByTelephone(String telephone){
		try{
			String queryString = "from Users as u where u.state="+SystemState.USE+" and " +
					"u.telephone=:telephone";
			Query query = getSession().createQuery(queryString);
			query.setString("telephone", telephone);
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 拉取所有用户
	 * @author zeb
	 * @since 2014年3月11日21:20:09
	 * @param truename
	 * @return
	 */
	public List<Users> getAllUsers(){
		List<Users> tempList =new ArrayList<Users>();
		try{
			String queryString = "from Users as u where u.state="+SystemState.USE;
			Query query = getSession().createQuery(queryString);
			tempList = query.list();
			return tempList;
		} catch (HibernateException e) {
			log.error("getAllUsers Exception:"+e.getMessage(),e);
		}
		return tempList;
	}
	
	/**
	 * 遍历所有可用的Users对象
	 * @author zeb
	 * @since 2014年3月11日21:20:20
	 * @param start
	 * @param limit
	 * @return
	 */
	public Map<String, Object> queryUsers(Integer start, Integer limit){
		Map<String, Object> map = new HashMap<String, Object>();
		try { 
			String queryString = "from Users  as u where u.state=:state ";
			String countString = "select count(*) from Users as u where u.state=:state ";
			Query queryObject = getSession().createQuery(queryString);
			Query countObject = getSession().createQuery(countString);
			queryObject.setFirstResult(start);
			queryObject.setMaxResults(limit);
			queryObject.setInteger("state", SystemState.USE);
			countObject.setInteger("state",SystemState.USE);
			List<Users> usersList = queryObject.list();
			Long count = (Long) countObject.uniqueResult();
			map.put("items", usersList);
			map.put("count", count);
			return map;
		} catch (HibernateException e) {
			throw e;
		}
	}
	
	/**
	 * 批量删除用户
	 * @author zeb
	 * @since 2014年3月11日21:20:29
	 * @param delData
	 * @throws Exception 
	 */
	public String delUsers(String delData) throws Exception{
		String userNames = "";
		try {
			String[] ids = delData.split(",");
			for (int i = 0; i < ids.length; i++) {
				Users t = findByUserId(Integer.valueOf(ids[i]));
				userNames += t.getUsername(); 
				t.setState(SystemState.NOUSE); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return userNames;
	}
	
	/**
	 * 通过用户Id找到用户对象
	 * @author zeb
	 * @since 2014年3月11日21:20:38
	 * @param userId
	 * @return
	 */
	public Users findByUserId(Integer userId){
		try{
			String queryString = "from Users as u where u.state="+SystemState.USE+" and " +
					"u.id=:userId";
			Query query = getSession().createQuery(queryString);
			query.setInteger("userId", userId);
			return (Users) query.uniqueResult();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 条件查询users对象（条件包括telephone，username，时间范围）
	 * @author zeb
	 * @since 2014年3月11日21:20:45
	 * @param start
	 * @param limit
	 * @param user
	 * @return
	 */
	public Map<String , Object> selectUsers(Integer start, Integer limit, Users user){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String queryString = "from Users as u where u.state="+SystemState.USE;
 			if (user != null) {
				if (user.getUsername() != null &&!user.getUsername().equals("")&&!user.getUsername().contains("'")) {
					queryString += " and u.username like '"+"%"+user.getUsername()+"%"+"'";
				}
				if (user.getTelephone() != null && !user.getTelephone().equals("")&&!user.getTelephone().contains("'")) {
					queryString += " and  u.telephone like '" +"%"+user.getTelephone()+"%"+"'";
				}
				if (user.getUsable() != null && !user.getUsable().equals("")) {
					queryString += " and  u.usable =" +user.getUsable();
				}
				//usercard暂存开始时间bankcard存结束时间
				if (user.getUsercard() != null && !user.getUsercard().equals("")&&!user.getUsercard().contains("'")) {
					queryString += " and  u.createtime >='"+user.getUsercard()+"'";
				}
				if (user.getBankcard() != null && !user.getBankcard().equals("")&&!user.getBankcard().contains("'")) {
					queryString += " and  u.createtime <='"+user.getBankcard()+"'";
				}
			}
			queryString += " order by u.id desc";
			String countString = "select count(*) "+queryString;
			Query queryObject = getSession().createQuery(queryString);
			Query countObject = getSession().createQuery(countString);
			queryObject.setFirstResult(start);
			queryObject.setMaxResults(limit);
			Long count = (Long) countObject.uniqueResult();
			List<Users> userTempList = queryObject.list();
			map.put("items", userTempList);
			map.put("count", count);
		} catch (HibernateException e) {
			// throw e;
			throw e;
		}
		return map;
	}

	
	/**
	 * 用户登录，验证用户名和密码
	 * @author zeb
	 * @since 2014年3月11日21:20:55
	 * @param user
	 * @return
	 */
	public List<Users> login(Users user){
		try{
			String queryString = "from Users as u where u.state="+SystemState.USE+" and u.telephone=:telephone and u.password=:password";
			Query query = getSession().createQuery(queryString);
			query.setString("telephone", user.getTelephone());
			query.setString("password", user.getPassword());
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 更新操作
	 * @param user
	 */
	public void update(Users user){
		try {
			getSession().update(user);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到所有含有当前用户Path的可用已激活的账户，可指定下找深度(所有小于等于depth的数据，如depth<=3)
	 * 可选择时间段用户量，返回深度，路径
	 * @author 张恩备
	 * @date 2016-3-14 下午03:43:38
	 */
	public List<Object[]> findDepthByUserPath(Users user,String path,Integer depth){
		try{
			String queryString = "SELECT u.depth,u.path FROM users AS u WHERE u.state ="+SystemState.USE+
			" AND u.usable <> "+SystemState.NOUSABLE+" AND u.depth <= "+depth+" AND u.path LIKE '"+path.trim()+"%'";
			if (user != null) {
				//usercard暂存开始时间bankcard存结束时间
				if (user.getUsercard() != null
						&& !user.getUsercard().equals("")
						&& !user.getUsercard().contains("'")) {
					queryString += " AND  u.createtime >='"
							+ user.getUsercard() + "'";
				}
				if (user.getBankcard() != null
						&& !user.getBankcard().equals("")
						&& !user.getBankcard().contains("'")) {
					queryString += " AND  u.createtime <='"
							+ user.getBankcard() + "'";
				}
			}
			Query query = getSession().createSQLQuery(queryString);
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 得到所有含有当前用户Path的可用已激活的账户，可指定查找层级（如depth=2）
	 * 可选择时间段用户量，返回深度，客户编码和路径
	 * @author 张恩备
	 * @date 2016-3-14 下午03:43:38
	 */
	public List<Object[]> findCodeByUserPath(Users user,String path,Integer depth){
		try{
			String queryString = "SELECT u.depth,u.usercode,u.path FROM users AS u WHERE u.state ="+SystemState.USE+
			" AND u.usable <> "+SystemState.NOUSABLE+" AND u.depth = "+depth+" AND u.path LIKE '"+path.trim()+"%'";
			if (user != null) {
				//usercard暂存开始时间bankcard存结束时间
				if (user.getUsercard() != null
						&& !user.getUsercard().equals("")
						&& !user.getUsercard().contains("'")) {
					queryString += " AND  u.createtime >='"
							+ user.getUsercard() + "'";
				}
				if (user.getBankcard() != null
						&& !user.getBankcard().equals("")
						&& !user.getBankcard().contains("'")) {
					queryString += " AND  u.createtime <='"
							+ user.getBankcard() + "'";
				}
			}
			Query query = getSession().createSQLQuery(queryString);
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
//	/**
//	 * 得到所有含有当前用户Path的可用已激活的账户
//	 * @author 张恩备
//	 * @date 2016-3-14 下午03:43:38
//	 */
//	public List<Object[]> findUserCodeByPath(String path){
//		try{
//			String queryString = "SELECT u.id,u.telephone,u.usercode,u.depth,u.path,u.createtime FROM users AS u " +
//			"WHERE u.state ="+SystemState.USE+" AND u.usable <> "+SystemState.NOUSABLE+" AND u.path LIKE '"+path.trim()+"%'";
//			Query query = getSession().createSQLQuery(queryString);
//			return query.list();
//		} catch (HibernateException e) {
//			// TODO Auto-generated catch block
//			throw e;
//		}
//	}
	/**
	 * 得到导出所有数据
	 * @author 张恩备
	 * @date 2016-3-14 下午03:43:38
	 */
	public List<Object[]> getExcelData(Users user){
		try{
			String queryString = "SELECT u.telephone,u.usercode,u.parentiphone,u.username,u.usercard,u.bankcard,u.bankname,u.bankaddress," +
					"u.createtime FROM users AS u WHERE u.state ="+SystemState.USE+" AND u.usable <> "+SystemState.NOUSABLE;
			if (user != null){
				//usercard暂存开始时间bankcard存结束时间
				if (user.getUsercard() != null && !user.getUsercard().equals("") && !user.getUsercard().contains("'")) {
					queryString += " AND  u.createtime >='"
							+ user.getUsercard() + "'";
				}
				if (user.getBankcard() != null && !user.getBankcard().equals("") && !user.getBankcard().contains("'")) {
					queryString += " AND  u.createtime <='"
							+ user.getBankcard() + "'";
				}
			}
			Query query = getSession().createSQLQuery(queryString);
			return query.list();
		} catch (HibernateException e) {
			log.error("Users_getExcelData Exception :"+e.getMessage(),e);
			throw e;
		}
	}
	/**********************自定义方法（↑）****************************/
	
	public void save(Users transientInstance) {
		log.debug("saving Users instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Users persistentInstance) {
		log.debug("deleting Users instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
}