package cn.itcast.elec.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecUserDao;
import cn.itcast.elec.domain.ElecUser;

@Repository(IElecUserDao.SERVICE_NAME)
public class ElecUserDaoImpl extends CommonDaoImpl<ElecUser> implements IElecUserDao {

	
	/**
	 * @Name: findElecPopedomByLogonName
	 * @Description : 使用登录名获取当前用户所具有的权限,查询数据库表
	 * 											elec_user
	 * 											elec_user_role
	 * 											elec_role_popedom
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-10
	 * @Parameters: String name  登录名
	 * @return: List<Object> 	用户存放该用户具有的权限	
	 */
	@Override
	public List<Object> findElecPopedomByLogonName(final String name) {
		final String sql = "select a.popedomcode as popedom from elec_role_popedom a " +
					 "left outer join elec_user_role b on a.RoleID = b.RoleID " +
					 "inner join elec_user c on b.UserID = c.UserID " +
					 "and c.logonName = ? " +
					 "where c.isDuty = '1' ";
		List<Object> list = (List<Object>)this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createSQLQuery(sql)
									  .addScalar("popedom", Hibernate.STRING);
				query.setParameter(0, name);
				return query.list();
			}
		});
		return list;
	}

	/**
	 * @Name: findElecRoleByLogonName
	 * @Description : 使用登录名获取当前用户所具有的权限,查询数据库表
	 * 											elec_user
	 * 											elec_user_role
	 * 											elec_systemddl
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-10
	 * @Parameters: String name  登录名
	 * @return: List<Object> 	存放该用户具有的角色集合	
	 */
	@Override
	public List<Object []> findElecRoleByLogonName(final String name) {
		final String sql = "select b.ddlCode as ddlCode,b.ddlName as ddlName from elec_user_role a " +
					"left outer join elec_systemddl b on a.RoleID = b.ddlCode " +
					"and b.keyword = '角色类型' "+
					"inner join elec_user c on a.UserID = c.UserID " +
					"and c.logonName = ? " +
					"where c.isDuty = '1' ";
		List<Object []> list = (List<Object []>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createSQLQuery(sql)
									.addScalar("ddlCode", Hibernate.STRING)
									.addScalar("ddlName", Hibernate.STRING);
				query.setParameter(0, name);
				return query.list();
			}
		});
		return list;
	}
	
}
