package com.zebsoft.right.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zebsoft.right.domain.Rolemenu;
import com.zebsoft.zzz.util.BaseHibernateDAO;
import com.zebsoft.zzz.util.SystemState;
/**
 * 说明：角色-菜单关系数据库操作
 * @author Thinker
 * since 2014年3月11日21:16:55
 * version 1.0
 */

@Repository("rolemenuDAO")
public class RolemenuDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RolemenuDAO.class);
	
	/**********************自定义方法（↓）****************************/
	/**
	 * 通过用户名Id在Rolemenu表中找到匹配的对象
	 * @author zeb
	 * @since 2014年3月11日21:17:19
	 */
	public List<Rolemenu> findMenuByRole(String roleId){
		try{
			String queryString = "from Rolemenu as rm where rm.state="+SystemState.USE+" and " +
					"rm.roleId.id=:roleId";
			Query query = getSession().createQuery(queryString);
			query.setString("roleId", roleId);
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	
	/**
	 * 通过roleId和menuId找到Rolemenu对象
	 * @author zeb
	 * @since 2014年3月11日21:17:31
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	public Rolemenu findByRoleIdAndMenuId(String roleId, String menuId){
		try{
			String queryString = "from Rolemenu as rm where rm.state="+SystemState.USE+" and " +
					"rm.menuId.id=:menuId and rm.roleId.id=:roleId";
			Query query = getSession().createQuery(queryString);
			query.setString("menuId", menuId);
			query.setString("roleId", roleId);
			return (Rolemenu) query.uniqueResult();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 更新操作
	 * @param user
	 */
	public void update(Rolemenu rolemenu){
		try {
			getSession().update(rolemenu);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**********************自定义方法（↑）****************************/

	public void save(Rolemenu transientInstance) {
		log.debug("saving Rolemenu instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Rolemenu persistentInstance) {
		log.debug("deleting Rolemenu instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

}