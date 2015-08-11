package cn.itcast.elec.service;

import java.util.Hashtable;
import java.util.List;

import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.web.form.ElecCommonMsgForm;
import cn.itcast.elec.web.form.ElecSystemDDlForm;
import cn.itcast.elec.web.form.ElecTextForm;
import cn.itcast.elec.web.form.ElecUserForm;

public interface IElecUserService {
	public final static String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecUserImpl";

	List<ElecUserForm> findELecUserList(ElecUserForm elecUserForm);

	void saveElecUser(ElecUserForm elecUserForm);

	ElecUserForm findELecUser(ElecUserForm elecUserForm);

	void deleteElecUser(ElecUserForm elecUserForm);

	String checkLogonName(String logonName);

	ElecUser findElecUserByLogonName(String name);

	String findElecPopedomByLogonName(String name);

	Hashtable<String, String> findElecRoleByLogonName(String name);

}
