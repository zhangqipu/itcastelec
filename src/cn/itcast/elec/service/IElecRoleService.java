package cn.itcast.elec.service;

import java.util.List;

import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.util.XMLObject;
import cn.itcast.elec.web.form.ElecRoleForm;
import cn.itcast.elec.web.form.ElecTextForm;
import cn.itcast.elec.web.form.ElecUserForm;

public interface IElecRoleService {
	public final static String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecRoleServiceImpl";

	List<XMLObject> readXml();

	List<XMLObject> readEditXml(String roleID);

	List<ElecUserForm> findElecUserByRoleID(String roleID);

	void saveRole(ElecRoleForm elecRoleForm);
	
}
