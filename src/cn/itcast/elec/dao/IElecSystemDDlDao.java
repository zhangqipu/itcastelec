package cn.itcast.elec.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.domain.ElecSystemDDl;
import cn.itcast.elec.domain.ElecText;

/**
 * @version V1.0.0
 * @author 屈卞忠
 * @create Date 2015-08-04
 * @purpost 提供数据字典的DAO层的接口
 * @param <ElecSystemDDlForm> 数据字典对象
 */
public interface IElecSystemDDlDao extends ICommonDao<ElecSystemDDl> {
	public final static String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecSystemDDlDaoImpl";

	List<Object> findKeyWord();

	String findDDlName(Integer sexID, String string);


}
