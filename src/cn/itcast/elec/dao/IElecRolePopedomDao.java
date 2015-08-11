package cn.itcast.elec.dao;

import cn.itcast.elec.domain.ElecRolePopedom;

/**
 * @version V1.0.0
 * @author 屈卞忠
 * @create Date 2015-08-08
 * @purpost 提供用户管理的DAO层的接口
 * @param <ElecRolePopedom> 用户的PO对象
 */
public interface IElecRolePopedomDao extends ICommonDao<ElecRolePopedom> {
	public final static String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecRolePopedomDaoImpl";

}
