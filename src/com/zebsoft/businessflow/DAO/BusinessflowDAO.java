package com.zebsoft.businessflow.DAO;

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

import com.zebsoft.businessflow.domain.Businessflow;
import com.zebsoft.zzz.util.BaseHibernateDAO;
import com.zebsoft.zzz.util.SystemState;

/**
 * A data access object (DAO) providing persistence and search support for
 * Businessflow entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zebsoft.businessflow.domain.Businessflow
 * @author MyEclipse Persistence Tools
 */
@Repository("businessflowDAO")
public class BusinessflowDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(BusinessflowDAO.class);
	
	/**********************自定义方法（↓）****************************/
	/**
	 * 通过交易流水查找List，有则返回List.size()大于0
	 * @author zeb
	 * @since 2014年3月11日21:20:00
	 * @param tradingflow
	 * @return
	 */
	public List<Businessflow> findByTradingflow(String tradingflow){
		try{
		String queryString = "from Businessflow as u where u.state="+SystemState.USE+" and " +
				"u.tradingflow=:tradingflow";
		Query query = getSession().createQuery(queryString);
		query.setString("tradingflow", tradingflow);
		return query.list();
	} catch (HibernateException e) {
		// TODO Auto-generated catch block
		throw e;
		}
	}
	/**
	 * 遍历所有可用的交易流水
	 * @author zeb
	 * @since 2014年3月11日21:20:20
	 * @param start
	 * @param limit
	 * @return
	 */
	public Map<String, Object> queryBusinessflow(Integer start, Integer limit){
		Map<String, Object> map = new HashMap<String, Object>();
		try { 
			String queryString = "from Businessflow  as u where u.state=:state ";
			String countString = "select count(*) from Businessflow as u where u.state=:state ";
			Query queryObject = getSession().createQuery(queryString);
			Query countObject = getSession().createQuery(countString);
			queryObject.setFirstResult(start);
			queryObject.setMaxResults(limit);
			queryObject.setInteger("state", SystemState.USE);
			countObject.setInteger("state",SystemState.USE);
			List<Businessflow> tempList = queryObject.list();
			Long count = (Long) countObject.uniqueResult();
			map.put("items", tempList);
			map.put("count", count);
			return map;
		} catch (HibernateException e) {
			throw e;
		}
	}
	/**
	 * 批量删除交易流水
	 * @author zeb
	 * @since 2014年3月11日21:20:29
	 * @param delData
	 * @throws Exception 
	 */
	public String delBusinessflow(String delData) throws Exception{
		String tradingflow = "";
		try {
			String[] ids = delData.split(",");
			for (int i = 0; i < ids.length; i++) {
				Businessflow t = findByPayId(ids[i]);
				tradingflow += t.getTradingflow(); 
				t.setState(SystemState.NOUSE); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return tradingflow;
	}
	
	/**
	 * 通过payId找到实体
	 * @author zeb
	 * @since 2014年3月11日21:20:38
	 * @param userId
	 * @return
	 */
	public Businessflow findByPayId(String payid){
		try{
			String queryString = "from Businessflow as u where u.state="+SystemState.USE+" and " +
					"u.payid=:payid";
			Query query = getSession().createQuery(queryString);
			query.setString("payid", payid);
			return (Businessflow) query.uniqueResult();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
			}
	}
	
	/**
	 * 条件查询交易流水（条件包括服务编号，手机号（客户编号），交易开始时间，结束时间）
	 * @author zeb
	 * @since 2014年3月11日21:20:45
	 * @param start
	 * @param limit
	 * @param flow
	 * @return
	 */
	public Map<String , Object> selectBusinessflow(Integer start, Integer limit, Businessflow flow){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String queryString = "from Businessflow as u where u.state="+SystemState.USE;
 			if (flow != null) {
				if (flow.getServicecode() != null &&!flow.getServicecode().equals("")&&!flow.getServicecode().contains("'")) {
					queryString += " and u.servicecode like '"+"%"+flow.getServicecode()+"%"+"'";
				}
				if (flow.getUsercode() != null && !flow.getUsercode().equals("")&&!flow.getUsercode().contains("'")) {
					queryString += " and  u.usercode = '"+flow.getUsercode()+"'";
				}
				//paydate开始时间,paytime结束时间，精确到天
				if (flow.getPaydate() != null && !flow.getPaydate().equals("")&&!flow.getPaydate().contains("'")) {
					queryString += " and  u.paydate >='"+flow.getPaydate()+"'";
				}
				if (flow.getPaytime() != null && !flow.getPaytime().equals("")&&!flow.getPaytime().contains("'")) {
					queryString += " and  u.paydate <='"+flow.getPaytime()+"'";
				}
			}
			queryString += " order by u.paydate desc";
			String countString = "select count(*) "+queryString;
			Query queryObject = getSession().createQuery(queryString);
			Query countObject = getSession().createQuery(countString);
			queryObject.setFirstResult(start);
			queryObject.setMaxResults(limit);
			Long count = (Long) countObject.uniqueResult();
			List<Businessflow> userTempList = queryObject.list();
			map.put("items", userTempList);
			map.put("count", count);
		} catch (HibernateException e) {
			// throw e;
			throw e;
		}
		return map;
	}
	
	/**
	 * 拉取一段时间内的交易流水(只提取服务编码，时间，交易金额)
	 * @author zeb
	 * @since 2014年3月11日21:20:45
	 * @param start
	 * @param limit
	 * @param flow
	 * @return
	 */
	public List<Object[]> partFlowByUser(Businessflow flow){
		try {
			String queryString = "SELECT b.servicecode,b.paydate,b.usercode,b.paymoney,b.payfee,b.paydesc " +
					"FROM businessflow AS b WHERE b.state="+SystemState.USE;
 			if (flow != null) {
//				if (flow.getServicecode() != null &&!flow.getServicecode().equals("")&&!flow.getServicecode().contains("'")) {
//					queryString += " and u.servicecode like '"+"%"+flow.getServicecode()+"%"+"'";
//				}
				if (flow.getUsercode() != null && !flow.getUsercode().equals("")&&!flow.getUsercode().contains("'")) {
					queryString += " AND  b.usercode = '"+flow.getUsercode()+"'";
				}
				//paydate开始时间,paytime结束时间，精确到天
				if (flow.getPaydate() != null && !flow.getPaydate().equals("")&&!flow.getPaydate().contains("'")) {
					queryString += " AND  b.paydate >='"+flow.getPaydate()+"'";
				}
				if (flow.getPaytime() != null && !flow.getPaytime().equals("")&&!flow.getPaytime().contains("'")) {
					queryString += " AND  b.paydate <='"+flow.getPaytime()+"'";
				}
			}
			queryString += " order by b.paydate desc";
			Query queryObject = getSession().createSQLQuery(queryString);
			return  queryObject.list();
		} catch (HibernateException e) {
			// throw e;
			throw e;
		}
	}
	
	/**
	 * 更新操作
	 * @param flow
	 */
	public void update(Businessflow flow){
		try {
			getSession().update(flow);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**********************自定义方法（↑）****************************/
	// property constants
	public static final String SERVICECODE = "servicecode";
	public static final String PAYCODE = "paycode";
	public static final String PAYDATE = "paydate";
	public static final String PAYTIME = "paytime";
	public static final String TRADINGFLOW = "tradingflow";
	public static final String LOCALDATE = "localdate";
	public static final String USERCODE = "usercode";
	public static final String USERIPHONE = "useriphone";
	public static final String TRADINGCHANNEL = "tradingchannel";
	public static final String ENDCODE = "endcode";
	public static final String PSAMCARD = "psamcard";
	public static final String BUSACQUIRER = "busacquirer";
	public static final String BUSFLOW = "busflow";
	public static final String BUSNUMBER = "busnumber";
	public static final String BUSDATE = "busdate";
	public static final String BUSTIME = "bustime";
	public static final String BUSENDDATE = "busenddate";
	public static final String SENDBUSFLOW = "sendbusflow";
	public static final String SENDBUSDATE = "sendbusdate";
	public static final String SENDBUSTIME = "sendbustime";
	public static final String PAYMAINACC = "paymainacc";
	public static final String PAYSECONDACC = "paysecondacc";
	public static final String PAYMONEY = "paymoney";
	public static final String PAYFEE = "payfee";
	public static final String PAYSTATE = "paystate";
	public static final String PAYDESC = "paydesc";
	public static final String PAYMARK = "paymark";
	public static final String STATE = "state";

	public void save(Businessflow transientInstance) {
		log.debug("saving Businessflow instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Businessflow persistentInstance) {
		log.debug("deleting Businessflow instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Businessflow findById(java.lang.String id) {
		log.debug("getting Businessflow instance with id: " + id);
		try {
			Businessflow instance = (Businessflow) getSession().get(
					"com.zebsoft.businessflow.domain.Businessflow", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Businessflow instance) {
		log.debug("finding Businessflow instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.zebsoft.businessflow.domain.Businessflow")
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
		log.debug("finding Businessflow instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Businessflow as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByServicecode(Object servicecode) {
		return findByProperty(SERVICECODE, servicecode);
	}

	public List findByPaycode(Object paycode) {
		return findByProperty(PAYCODE, paycode);
	}

	public List findByPaydate(Object paydate) {
		return findByProperty(PAYDATE, paydate);
	}

	public List findByPaytime(Object paytime) {
		return findByProperty(PAYTIME, paytime);
	}

	public List findByTradingflow(Object tradingflow) {
		return findByProperty(TRADINGFLOW, tradingflow);
	}

	public List findByLocaldate(Object localdate) {
		return findByProperty(LOCALDATE, localdate);
	}

	public List findByUsercode(Object usercode) {
		return findByProperty(USERCODE, usercode);
	}

	public List findByUseriphone(Object useriphone) {
		return findByProperty(USERIPHONE, useriphone);
	}

	public List findByTradingchannel(Object tradingchannel) {
		return findByProperty(TRADINGCHANNEL, tradingchannel);
	}

	public List findByEndcode(Object endcode) {
		return findByProperty(ENDCODE, endcode);
	}

	public List findByPsamcard(Object psamcard) {
		return findByProperty(PSAMCARD, psamcard);
	}

	public List findByBusacquirer(Object busacquirer) {
		return findByProperty(BUSACQUIRER, busacquirer);
	}

	public List findByBusflow(Object busflow) {
		return findByProperty(BUSFLOW, busflow);
	}

	public List findByBusnumber(Object busnumber) {
		return findByProperty(BUSNUMBER, busnumber);
	}

	public List findByBusdate(Object busdate) {
		return findByProperty(BUSDATE, busdate);
	}

	public List findByBustime(Object bustime) {
		return findByProperty(BUSTIME, bustime);
	}

	public List findByBusenddate(Object busenddate) {
		return findByProperty(BUSENDDATE, busenddate);
	}

	public List findBySendbusflow(Object sendbusflow) {
		return findByProperty(SENDBUSFLOW, sendbusflow);
	}

	public List findBySendbusdate(Object sendbusdate) {
		return findByProperty(SENDBUSDATE, sendbusdate);
	}

	public List findBySendbustime(Object sendbustime) {
		return findByProperty(SENDBUSTIME, sendbustime);
	}

	public List findByPaymainacc(Object paymainacc) {
		return findByProperty(PAYMAINACC, paymainacc);
	}

	public List findByPaysecondacc(Object paysecondacc) {
		return findByProperty(PAYSECONDACC, paysecondacc);
	}

	public List findByPaymoney(Object paymoney) {
		return findByProperty(PAYMONEY, paymoney);
	}

	public List findByPayfee(Object payfee) {
		return findByProperty(PAYFEE, payfee);
	}

	public List findByPaystate(Object paystate) {
		return findByProperty(PAYSTATE, paystate);
	}

	public List findByPaydesc(Object paydesc) {
		return findByProperty(PAYDESC, paydesc);
	}

	public List findByPaymark(Object paymark) {
		return findByProperty(PAYMARK, paymark);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all Businessflow instances");
		try {
			String queryString = "from Businessflow";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Businessflow merge(Businessflow detachedInstance) {
		log.debug("merging Businessflow instance");
		try {
			Businessflow result = (Businessflow) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Businessflow instance) {
		log.debug("attaching dirty Businessflow instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Businessflow instance) {
		log.debug("attaching clean Businessflow instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}