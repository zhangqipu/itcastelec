package cn.itcast.elec.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import cn.itcast.elec.dao.IElecCommonMsgDao;
import cn.itcast.elec.domain.ElecCommonMsg;

/**
 * @version V1.0.0
 * @create Date 2015-07-30
 * @author 屈卞忠
 *
 */
@Component(IElecCommonMsgDao.SERVICE_NAME)
public class ElecCommonMsgDaoImpl extends CommonDaoImpl<ElecCommonMsg> implements IElecCommonMsgDao {

	/**
	 * @Name:findElecCommonMsgListByCurrentDate
	 * @Description : 通过当前日期查询待办事宜列表
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: null
	 * @return: List<Object[]> 代办事宜列表
	 */
	public List<Object[]> findElecCommonMsgListByCurrentDate(String currentDate) {
		final String sql =    "select o.StationRun as stationRun,o.DevRun as devRun " + 
						"from Elec_CommonMsg o " + 
						"where o.CreateDate = '" +
						currentDate+"'";
		List<Object[]> list = (List<Object[]>)this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createSQLQuery(sql)
									.addScalar("stationRun", Hibernate.STRING)
									.addScalar("devRun", Hibernate.STRING);
				return query.list();
			}
		});
		return list;
	}


}
