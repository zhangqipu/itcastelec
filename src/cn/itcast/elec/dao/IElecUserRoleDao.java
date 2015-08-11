package cn.itcast.elec.dao;

import java.util.List;

import cn.itcast.elec.domain.ElecUserRole;

/**
 * @version V1.0.0
 * @author 屈卞忠
 * @create Date 2015-08-08
 * @purpost 提供用户管理的DAO层的接口
 * @param <ElecUserRole> 用户的PO对象
 */
public interface IElecUserRoleDao extends ICommonDao<ElecUserRole> {
	public final static String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecUserRoleDaoImpl";

	List<Object[]> findElecUserListByRoleID(String roleID);

}
