package com.zebsoft.right.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zebsoft.right.domain.Roleusers;
import com.zebsoft.zzz.util.BaseHibernateDAO;
import com.zebsoft.zzz.util.SystemState;
/**
 * 说明：用户-角色关系数据库操作
 * @author Thinker
 * since 2014年3月11日21:16:55
 * version 1.0
 */

@Repository("roleusersDAO")
public class RoleusersDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RoleusersDAO.class);
	
	
	
	
	/**********************自定义方法（↓）****************************/
	/**
	 * 通过用户名Id在Roleusers表中找到匹配的对象
	 * @author zeb
	 * @since 2014年3月11日21:18:36
	 */
	public List<Roleusers> findRoleByUser(String userId){
		try{
			String queryString = "from Roleusers as ru where ru.state="+SystemState.USE+" and " +
					"ru.usersId.id=:userId";
			Query query = getSession().createQuery(queryString);
			query.setString("userId", userId);
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	/**
	 * 通过userId和roleId找到唯一的Roleusers对象
	 * @author zeb
	 * @since 2014年3月11日21:18:47
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public Roleusers findByUserIdAndRoleId(String userId , String roleId){
		try{
			String queryString = "from Roleusers as ru where ru.state="+SystemState.USE+" and " +
					"ru.usersId.id=:userId and ru.roleId.id=:roleId";
			Query query = getSession().createQuery(queryString);
			query.setString("userId", userId);
			query.setString("roleId", roleId);
			return (Roleusers) query.uniqueResult();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 更新操作
	 * @param user
	 */
	public void update(Roleusers roleusers){
		try {
			getSession().update(roleusers);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**********************自定义方法（↑）****************************/

	public void save(Roleusers transientInstance) {
		log.debug("saving Roleusers instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Roleusers persistentInstance) {
		log.debug("deleting Roleusers instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

}