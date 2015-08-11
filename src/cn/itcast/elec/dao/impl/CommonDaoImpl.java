package cn.itcast.elec.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.ICommonDao;
import cn.itcast.elec.util.GenericSuperClass;
/**
 * 
 * @author 屈卞忠
 * @create Date 2015-07-30
 * @param <T> 泛型对象
 * @return null
 * @describle 实现添加一个对象的功能，继承了HibernateDaoSupport类，实现了ICommonDaoImpl接口
 */
public class CommonDaoImpl<T> extends HibernateDaoSupport implements ICommonDao<T> {

	//泛型转换
	@SuppressWarnings("rawtypes")
	private Class entity = (Class)GenericSuperClass.getClass(this.getClass());

	/**
	 * @Name:save
	 * @Description : 保存对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: T t 范类
	 * @return:返回 null
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	/**
	 * @Name:setSessionFactoryDi
	 * @Description : 获取SessionFactory
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: SessionFactoryDi sessionFactoryDi 会话工厂
	 * @return:返回 null
	 */
	@Resource(name="sessionFactory")
	public final void setSessionFactoryDi(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * @Name:update
	 * @Description : 更新对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: T t 范类
	 * @return:返回 null
	 */
	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	/**
	 * @Name:findObjectById
	 * @Description : 查找一个对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: T t 范类
	 * @return:返回 T 
	 */
	@Override
	public T findObjectById(Serializable id) {
		return (T) this.getHibernateTemplate().get(entity, id);
	}

	/**
	 * @Name:deleteObjectByIds
	 * @Description : 通过id的数组来删除一个对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: T t 范类
	 * @return:返回 null
	 */
	@Override
	public void deleteObjectByIds(Serializable... ids) {
		for(int i=0;ids!=null && i<ids.length;i++){
			Serializable id = ids[i];
			Object object = (Object)this.getHibernateTemplate().get(entity, id);
			this.getHibernateTemplate().delete(object);
		}
	}
	/**
	 * @Name:deleteObjectByCollection
	 * @Description : 通过集合来删除一个对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: Collection<T> list 集合对象
	 * @return:返回 null
	 */
	@Override
	public void deleteObjectByCollection(Collection<T> entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}
	/**
	 * @Name:findCollectionByConditionNoPage
	 * @Description : 通过集合来删除一个对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: String hqlWhere,hql语句的where条件
					Object[] params, where条件的查询条件
					LinkedHashMap<String, String> orderby,orderby排序条件
	 * @return: List<T>  结果集列表
	 */
	@Override
	public List<T> findCollectionByConditionNoPage(String hqlWhere,
			final Object[] params, LinkedHashMap<String, String> orderby) {
		
		/**
		 * 组织HQL语句的where条件
		 * 
		 * select * from elec_text o where 1=1		//放置DAO层
		 * where o.textName like ?			//放置Service层
		 * and o.textRemark like ?
		 * order by o.textDate desc , o.textName asc;
		 */
		String hql = "from "+entity.getSimpleName()+" o where 1=1 ";
		//组织排序条件
		String hqlOrderBy = this.orderByCondition(orderby);
		hql = hql + hqlWhere + hqlOrderBy;
		final String finalHql = hql;
		List<T> list = (List<T>)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(finalHql);
				setParams(query,params);
				
				return query.list();
			}
		});
		return list;
	}
	/**
	 * @Name:setParams
	 * @Description : 对where条件中的参数设置参数值
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: Object[] params 参数值
	 * @return: null
	 */
	private void setParams(Query query,Object[] params) {
		// TODO Auto-generated method stub
		for(int i=0;params != null && i<params.length;i++){
			query.setParameter(i, params[i]);
		}
	}

	/**
	 * @Name:orderByCondition
	 * @Description : 组织排序条件
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: LinkedHashMap<String, String> orderby orderby 排序条件
	 * @return: String  排序语句的字符串
	 */
	private String orderByCondition(LinkedHashMap<String, String> orderby) {
		StringBuffer buffer = new StringBuffer("");
		
		if(orderby != null){
			buffer.append(" order by");
			for(Map.Entry<String, String> map : orderby.entrySet()){
				buffer.append(" " + map.getKey() + " " + map.getValue() + ",");
			}
			buffer.deleteCharAt(buffer.length()-1);
		}
		return buffer.toString();
	}

	/**
	 * @Name: saveObjectByCollection
	 * @Description : 使用集合的形式进行批量保存
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: Collection<T> entities 集合对象
	 * @return: null
	 * 
	 */
	@Override
	public void saveObjectByCollection(Collection<T> entities) {
		this.getHibernateTemplate().saveOrUpdateAll(entities);
	}

	

}
