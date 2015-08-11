package cn.itcast.elec.web.form;

import java.io.Serializable;
import java.util.Date;

/**
 * PO持久层对象，对应数据库表Elec_SystemDDl
 * @author 屈卞忠
 *
 */
public class ElecRoleForm implements Serializable {

	private String role;			//角色 ID
	private String roleid;			//角色 ID
	private String [] selectoper;	//权限编号（权限code）
	private String [] selectuser;	//用户ID
	
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String[] getSelectoper() {
		return selectoper;
	}

	public void setSelectoper(String[] selectoper) {
		this.selectoper = selectoper;
	}

	public String[] getSelectuser() {
		return selectuser;
	}

	public void setSelectuser(String[] selectuser) {
		this.selectuser = selectuser;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
