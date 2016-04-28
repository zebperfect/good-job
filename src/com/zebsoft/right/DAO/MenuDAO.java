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

import com.zebsoft.right.domain.Menu;
import com.zebsoft.zzz.util.BaseHibernateDAO;
import com.zebsoft.zzz.util.SystemState;
/**
 * 说明：菜单管理数据库操作
 * @author Thinker
 * 时间：2014年3月11日21:11:16
 * @version 1.0
 */

@Repository("menuDAO")
public class MenuDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(MenuDAO.class);
	
	/**********************自定义方法（↓）****************************/
	/**
	 * 根据菜单Id找到该菜单对象
	 * @author zeb
	 * @since 2014年3月11日21:14:50
	 */
	public Menu findMenuById(String menuId){
		try{
			String queryString = "from Menu as m where m.state="+SystemState.USE+" and " +
					"m.id=:menuId";
			Query query = getSession().createQuery(queryString);
			query.setString("menuId", menuId);
			return (Menu) query.uniqueResult();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 找到所有可用的菜单(包括一级和二级菜单)
	 * @author zeb
	 * @since 2014年3月11日21:14:58
	 * @return
	 */
	public List<Menu> findAllMenu(){
		try{
			String queryString = "from Menu as m where m.state="+SystemState.USE;
			Query query = getSession().createQuery(queryString);
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 找到所有可用的二级菜单(List形式)
	 * @author zeb
	 * @since 2014年3月11日21:15:06
	 * @return
	 */
	public List<Menu> findAllSecondMenu(){
		try{
			String queryString = "from Menu as m where m.state="+SystemState.USE+
			" and m.parent.parent.id='1'";
			Query query = getSession().createQuery(queryString);
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 找到所有可用的二级菜单(Map形式)
	 * @author zeb
	 * @since 2014年3月11日21:15:14
	 * @return
	 */
	public Map<String , Object> findAllSecondMenu(Integer start, Integer limit){
			Map<String, Object> map = new HashMap<String, Object>();
			try{ 
				String queryString = "from Menu as m where m.state="+SystemState.USE+
					" and m.parent.parent.id='1'";
				String countString = "select count(*) from Menu as m where m.state="+SystemState.USE+
					" and m.parent.parent.id='1'";
				Query queryObject = getSession().createQuery(queryString);
				Query countObject = getSession().createQuery(countString);
//				queryObject.setFirstResult(start);
//				queryObject.setMaxResults(limit);
				List<Menu> menuList = queryObject.list();
				Long count = (Long) countObject.uniqueResult();
				map.put("items", menuList);
				map.put("count", count);
				return map;
			} catch (HibernateException e) {
				throw e;
			}
	}
	
	/**
	 * 找到所有可用的一级菜单
	 * @author zeb
	 * @since 2014年3月11日21:15:29
	 * @return
	 */
	public List<Menu> findAllFirstMenu(){
		try{
			String queryString = "from Menu as m where m.state="+SystemState.USE+
			" and m.parent.id='1'";
			Query query = getSession().createQuery(queryString);
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	
	/**********************自定义方法（↑）****************************/

	public void save(Menu transientInstance) {
		log.debug("saving Menu instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Menu persistentInstance) {
		log.debug("deleting Menu instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

}