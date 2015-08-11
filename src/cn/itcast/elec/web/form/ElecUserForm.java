package cn.itcast.elec.web.form;

import java.io.Serializable;
import java.util.Date;

/**
 * PO持久层对象，对应数据库表Elec_SystemDDl
 * @author 屈卞忠
 *
 */
public class ElecUserForm implements Serializable {

	private String userID;			//主键ID
	private String jctID;			//所属单位code
	private String userName;		//用户姓名
	private String logonName;		//登录名
	private String logonPwd;		//密码
	private String sexID;			//性别
	private String birthday;			//出生日期
	private String address;			//联系地址
	private String contactTel;		//联系电话 
	private String email;			//电子邮箱
	private String mobile;			//手机	
	private String isDuty;			//是否在职	
	private String onDutyDate;		//入职时间
	private String offDutyDate;		//离职时间
	private String remark;			//备注
	/**
	 * 使用ViewFlag字段
	 * 判断当前用户操作的是编辑页面还是明细页面
	 * 如果viewflag==null:说明当前操作的是编辑页面
	 * 如果viewflag==1：说明当前操作的是明细页面
	 */
	private String viewflag;
	/**
	 * 使用flagziduan 来判断角色编辑的页面中，该用户是否被选中
	 * 如果flag = 0，表示该角色不拥有此用户，则页面中的复选框不被选中
	 * 如果flag = 1，表示该角色拥有此用户，则页面中的复选框被选中
	 */
	private String flag;
	/**
	 * 	   * 处理当前用户是否修改了密码，利用md5flag进行标识判断
	         * 如果当前用户修改了密码，则保存运行时，需要对密码进行加密，然后保存加密后的密码
	            * 设置md5flag == null
	         * 如果当前用户没有修改密码，则保存运行时，不需要对密码进行加密，直接保存当前密码即可
	            * 设置md5falg == 1
	 */
	private String md5flag;
	/**
	 * 用于判断当前操作者具有的角色是否是 系统管理员 、 高级管理员的标识。
	 * 		如果当前操作者是系统管理员、高级管理员，则 点击 “用户管理” 时，
	 * 		就跳转到userIndex.jsp，可以查看用户列表信息。
	 * 		如果当前操作者不是系统管理员、高级管理员的时候，点击 “用户管理” 时，
	 * 		就跳转到userEdit.jsp，可以对当前登录人进行编辑并保存
	 *  		如果跳转到userEdit.jsp ，点击“保存”的时候，需要重定向到userEdit.jsp
	 *  		此时设置重定向的标识使用roleflag：
	 *  			当roleflag==1时，需要跳转到userEdit.jsp
	 *  			当roleflag==null时，需要跳转到userIndex.jsp
	 */
	private String roleflag;
	
	public String getRoleflag() {
		return roleflag;
	}
	public void setRoleflag(String roleflag) {
		this.roleflag = roleflag;
	}
	public String getMd5flag() {
		return md5flag;
	}
	public void setMd5flag(String md5flag) {
		this.md5flag = md5flag;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getViewflag() {
		return viewflag;
	}
	public void setViewflag(String viewflag) {
		this.viewflag = viewflag;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getJctID() {
		return jctID;
	}
	public void setJctID(String jctID) {
		this.jctID = jctID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogonName() {
		return logonName;
	}
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}
	public String getLogonPwd() {
		return logonPwd;
	}
	public void setLogonPwd(String logonPwd) {
		this.logonPwd = logonPwd;
	}
	public String getSexID() {
		return sexID;
	}
	public void setSexID(String sexID) {
		this.sexID = sexID;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIsDuty() {
		return isDuty;
	}
	public void setIsDuty(String isDuty) {
		this.isDuty = isDuty;
	}
	public String getOnDutyDate() {
		return onDutyDate;
	}
	public void setOnDutyDate(String onDutyDate) {
		this.onDutyDate = onDutyDate;
	}
	public String getOffDutyDate() {
		return offDutyDate;
	}
	public void setOffDutyDate(String offDutyDate) {
		this.offDutyDate = offDutyDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
