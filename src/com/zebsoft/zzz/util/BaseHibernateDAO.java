package com.zebsoft.zzz.util;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO {

	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 
	 * 多条件查询完全匹配的方法，两个Criteria分别用于查询记录，统计记录数目
	 * 
	 * @param criteria
	 *            第一个Criteria
	 * @param criteria2
	 *            第二个Criteria
	 * @param str
	 *            POJO内的字段名
	 * @param object
	 *            需要匹配的对象
	 * 
	 */
	public void addEQConditions(Criteria criteria, Criteria criteria2, String str, Object object) {
		criteria.add(Restrictions.eq(str, object));
		criteria2.add(Restrictions.eq(str, object));
	}

	/**
	 * 
	 * 多条件查询模糊查询的方法，两个Criteria分别用于查询记录，统计记录数目
	 * 
	 * @param criteria
	 *            第一个Criteria
	 * @param criteria2
	 *            第二个Criteria
	 * @param str
	 *            POJO内的字段名
	 * @param object
	 *            需要匹配的对象
	 * 
	 */
	public void addLIKEConditions(Criteria criteria, Criteria criteria2, String str, Object object) {
		criteria.add(Restrictions.like(str, object));
		criteria2.add(Restrictions.like(str, object));
	}

	/**
	 * 
	 * 统计数目的方法
	 * 
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public int criteriaCount(Criteria criteria) {
		return Integer.parseInt(criteria.setProjection(Projections.rowCount()).list().get(0).toString());
	}

	/**
	 * 
	 * 查询记录的方法
	 * 
	 * @param criteria
	 * @param start
	 *            数据库查询开始值
	 * @param limit
	 *            数据库查询限量值
	 * @return
	 * @throws Exception
	 */
	public List<?> criteriaList(Criteria criteria, int start, int limit) {
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	/**
	 * 
	 * @param classTemp
	 *            需要查询的POJO类
	 * @param str
	 *            ID的字段名
	 * @param object
	 *            匹配的ID值
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<?> commonFindById(Class classTemp, String str, Object object) {
		Criteria criteria = getSession().createCriteria(classTemp);
		return criteria.add(Restrictions.eq(str, object)).list();
	}
	
	
	/**
	 * 
	 *  结果集降序排序
	 * @param criteria
	 * @param criteria2
	 * @param str
	 */
	public void orderDesc(Criteria criteria, Criteria criteria2, String str){
		criteria.addOrder(Order.desc(str));
		criteria2.addOrder(Order.desc(str));
	}
}