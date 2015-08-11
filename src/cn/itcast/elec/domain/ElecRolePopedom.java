package cn.itcast.elec.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * PO持久层对象，对应数据库表Elec_Role_Popedom
 * @author 屈卞忠
 *
 */
@SuppressWarnings("serial")
public class ElecRolePopedom implements Serializable {
	
	private String roleID;			//主键ID
	private String popedomcode;		//配置web文件中权限的编码code的字符串连接
	private String remark;			//备注
	
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getPopedomcode() {
		return popedomcode;
	}
	public void setPopedomcode(String popedomcode) {
		this.popedomcode = popedomcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
