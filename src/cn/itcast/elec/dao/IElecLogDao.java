package cn.itcast.elec.dao;

import java.util.List;

import cn.itcast.elec.domain.ElecLog;
import cn.itcast.elec.domain.ElecSystemDDl;
import cn.itcast.elec.domain.ElecUser;

/**
 * @version V1.0.0
 * @author 屈卞忠
 * @create Date 2015-08-11
 * @purpost 提供日志管理的DAO层的接口
 * @param <ElecLog> Log的PO对象
 */
public interface IElecLogDao extends ICommonDao<ElecLog> {
	public final static String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecLogDaoImpl";

}
