package cn.itcast.elec.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.itcast.elec.dao.IElecSystemDDlDao;
import cn.itcast.elec.domain.ElecSystemDDl;

/**
 * @version V1.0.0
 * @create Date 2015-07-30
 * @author 屈卞忠
 *
 */
@Component(IElecSystemDDlDao.SERVICE_NAME)
public class ElecSystemDDlDaoImpl extends CommonDaoImpl<ElecSystemDDl> implements IElecSystemDDlDao {

	/**
	 * @Name:findKeyWord
	 * @Description : 查询数据类型关键字，且去掉重复值
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: null
	 * @return: List<ElecSystemDDl> 数据类型列表
	 */
	@Override
	public List<Object> findKeyWord() {
		String hql = "select distinct o.keyword from ElecSystemDDl o ";
		List<Object> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * @Name: findDDlName
	 * @Description : 使用数据类型和数据项的编号获取数据项的名称
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: String sexID 数据项的编号
	 * 				 String string 数据类型
	 * @return: String 数据项名称
	 */
	@Override
	public String findDDlName(Integer ddlCode, String keyword) {
		String hql = "from ElecSystemDDl where keyword= '" + keyword + "' and ddlCode=" + ddlCode;
		List<ElecSystemDDl> list = this.getHibernateTemplate().find(hql);
		String ddlName = "";
		if(list != null && list.size()>0){
			ElecSystemDDl elecSystemDDl = list.get(0);
			ddlName = elecSystemDDl.getDdlName();
		}
		return ddlName;
	}

	
	
}
