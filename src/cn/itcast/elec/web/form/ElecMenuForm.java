package cn.itcast.elec.web.form;

import java.io.Serializable;

import cn.itcast.elec.web.action.BaseAction;
/**
 * VO对象，对应页面表单的属性值（首页显示）
 * @author Administrator
 *
 */
public class ElecMenuForm implements Serializable{
	private String name;
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
