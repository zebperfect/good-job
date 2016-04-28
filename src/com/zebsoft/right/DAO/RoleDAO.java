package com.zebsoft.right.DAO;

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

import com.zebsoft.right.domain.Role;
import com.zebsoft.zzz.util.BaseHibernateDAO;
import com.zebsoft.zzz.util.SystemState;
/**
 * 说明：角色管理数据库操作
 * @author Thinker
 * 时间：2014年3月11日21:11:16
 * @version 1.0
 */

@Repository("roleDAO")
public class RoleDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(RoleDAO.class);
	
	
	/**********************自定义方法（↓）****************************/
	/**
	 *  通过角色名称查找role对象，有则返回List.size()大于0
	 *  @author Thinker
	 *  时间：2014年3月11日21:11:16
	 *  @version 1.0
	 */
	public Role findRoleById(String roleId){
		try{
			String queryString = "from Role as r where r.state="+SystemState.USE+" and " +
					"r.id=:roleId";
			Query query = getSession().createQuery(queryString);
			query.setString("roleId", roleId);
			return (Role) query.uniqueResult();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 通过角色名称查找roleList，有则返回List.size()大于0
	 * @author zeb
	 * @since 2014年3月11日21:13:58
	 * @param roleName
	 * @return
	 */
	public List<Role> findRoleByName(String roleName){
		try{
		String queryString = "from Role as r where r.state="+SystemState.USE+" and " +
				"r.name=:roleName";
		Query query = getSession().createQuery(queryString);
		query.setString("roleName", roleName);
		return query.list();
	} catch (HibernateException e) {
		// TODO Auto-generated catch block
		throw e;
		}
	}
	
	/**
	 * 遍历所有可用的Role对象
	 * @author zeb
	 * @since 2014年3月11日21:14:10
	 * @param start
	 * @param limit
	 * @return
	 */
	public Map<String, Object> queryRole(Integer start, Integer limit){
		Map<String, Object> map = new HashMap<String, Object>();
		try { 
			String queryString = "from Role  as r where r.state=:state ";
			String countString = "select count(*) from Role as r where r.state=:state ";
			Query queryObject = getSession().createQuery(queryString);
			Query countObject = getSession().createQuery(countString);
			queryObject.setFirstResult(start);
			queryObject.setMaxResults(limit);
			queryObject.setInteger("state", SystemState.USE);
			countObject.setInteger("state",SystemState.USE);
			List<Role> roleList = queryObject.list();
			Long count = (Long) countObject.uniqueResult();
			map.put("items", roleList);
			map.put("count", count);
			return map;
		} catch (HibernateException e) {
			throw e;
		}
	}
	
	/**
	 * 批量删除角色
	 * @author zeb
	 * @since 2014年3月11日21:14:21
	 * @param delData
	 * @throws Exception 
	 */
	public String delRole(String delData) throws Exception{
		String roleNames = "";
		try {
			String[] ids = delData.split(",");
			for (int i = 0; i < ids.length; i++) {
				Role t = findRoleById(ids[i]);
				roleNames += t.getName(); 
				t.setState(SystemState.NOUSE); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return roleNames;
	}
	
	/**********************自定义方法（↑）****************************/

	public void save(Role transientInstance) {
		log.debug("saving Role instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Role persistentInstance) {
		log.debug("deleting Role instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

}