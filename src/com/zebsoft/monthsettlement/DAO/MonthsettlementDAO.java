package com.zebsoft.monthsettlement.DAO;

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

import com.zebsoft.monthsettlement.domain.Monthsettlement;
import com.zebsoft.zzz.util.BaseHibernateDAO;
import com.zebsoft.zzz.util.SystemState;

/**
 * A data access object (DAO) providing persistence and search support for
 * Monthsettlement entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zebsoft.monthsettlement.domain.Monthsettlement
 * @author MyEclipse Persistence Tools
 */
@Repository("monthsettlementDAO")
public class MonthsettlementDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(MonthsettlementDAO.class);
	/**********************自定义方法（↓）****************************/
	/**
	 * 条件查询月结清单（条件包括手机号码，月结月份）
	 * @author zeb
	 * @since 2014年3月11日21:20:45
	 * @param start
	 * @param limit
	 * @param msettle
	 * @return
	 */
	public List<Monthsettlement> selectSettlement(Integer start, Integer limit, String queryTelephone,String queryDate){
		List<Monthsettlement> msList = new ArrayList<Monthsettlement>();
		try {
			String queryString = "from Monthsettlement as u where u.state="+SystemState.USE;
			if (queryTelephone != null &&!"".equals(queryTelephone)&&!queryTelephone.contains("'")) {
				queryString += " and u.telephone = '"+queryTelephone+"'";
			}
			if (queryDate != null && !"".equals(queryDate)&&!queryDate.contains("'")) {
				queryString += " and  u.inmonth ='"+queryDate+"'";
			}
			String countString = "select count(*) "+queryString;
			queryString += " order by u.totalmoney desc";
			Query queryObject = getSession().createQuery(queryString);
			Query countObject = getSession().createQuery(countString);
			queryObject.setFirstResult(start);
			queryObject.setMaxResults(limit);
			Long count = (Long) countObject.uniqueResult();
			msList = queryObject.list();
		} catch (HibernateException e) {
			log.error("selectSettlement Exception:"+e.getMessage(),e);
		}
		return msList;
	}
	/**
	 * 获取导出数据
	 * @author 张恩备
	 * @date 2016-4-22 下午04:06:42
	 */
	public List<Object[]> getExcelData(String queryTelephone, String queryDate) {
		try{
			String queryString = "SELECT m.telephone,u.usercode,u.username,u.usercard,u.bankname,u.bankcard,u.bankaddress,m.profitmoney,m.devicemoney,m.totalmoney,m.inmonth " +
					"FROM monthsettlement AS m ,users AS u WHERE m.userid = u.id AND  u.state ="+SystemState.USE+"";
			if (queryTelephone != null &&!"".equals(queryTelephone)&&!queryTelephone.contains("'")) {
				queryString += " and m.telephone = '"+queryTelephone+"'";
			}
			if (queryDate != null && !"".equals(queryDate)&&!queryDate.contains("'")) {
				queryString += " and  m.inmonth ='"+queryDate+"'";
			}
			Query query = getSession().createSQLQuery(queryString);
			return query.list();
		} catch (HibernateException e) {
			log.error("getExcelData Exception:"+e.getMessage(),e);
			throw e;
		}
	}
	
	/**
	 * 查找是否已生成上月结算，有异常按不存在处理
	 * @author 张恩备
	 * @date 2016-4-26 上午11:23:11
	 */
	public boolean findInmonth(String inmonth) {
		try {
			String queryString = "from Monthsettlement as u where u.state="+SystemState.USE;
			if (inmonth != null && !"".equals(inmonth)&&!inmonth.contains("'")) {
				queryString += " and  u.inmonth ='"+inmonth+"'";
			}
			String countString = "select count(*) "+queryString;
			Query countObject = getSession().createQuery(countString);
			Long count = (Long) countObject.uniqueResult();
			if(count > 0){
				return true;
			}else{
				return false;
			}
		} catch (HibernateException e) {
			log.error("findInmonth Exception:"+e.getMessage(),e);
			return false;
		}
	}
	/**********************自定义方法（↑）****************************/
	// property constants
	public static final String USERID = "userid";
	public static final String PROFITMONEY = "profitmoney";
	public static final String DEVICEMONEY = "devicemoney";
	public static final String TOTALMONEY = "totalmoney";
	public static final String INMONTH = "inmonth";
	public static final String STATE = "state";

	public void save(Monthsettlement transientInstance) {
		log.debug("saving Monthsettlement instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Monthsettlement persistentInstance) {
		log.debug("deleting Monthsettlement instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Monthsettlement findById(java.lang.String id) {
		log.debug("getting Monthsettlement instance with id: " + id);
		try {
			Monthsettlement instance = (Monthsettlement) getSession().get(
					"com.zebsoft.monthsettlement.domain.Monthsettlement", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Monthsettlement instance) {
		log.debug("finding Monthsettlement instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.zebsoft.monthsettlement.domain.Monthsettlement")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Monthsettlement instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Monthsettlement as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findByProfitmoney(Object profitmoney) {
		return findByProperty(PROFITMONEY, profitmoney);
	}

	public List findByDevicemoney(Object devicemoney) {
		return findByProperty(DEVICEMONEY, devicemoney);
	}

	public List findByTotalmoney(Object totalmoney) {
		return findByProperty(TOTALMONEY, totalmoney);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all Monthsettlement instances");
		try {
			String queryString = "from Monthsettlement";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Monthsettlement merge(Monthsettlement detachedInstance) {
		log.debug("merging Monthsettlement instance");
		try {
			Monthsettlement result = (Monthsettlement) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Monthsettlement instance) {
		log.debug("attaching dirty Monthsettlement instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Monthsettlement instance) {
		log.debug("attaching clean Monthsettlement instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

}