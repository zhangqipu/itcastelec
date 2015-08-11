package cn.itcast.elec.web.action;

import java.util.List;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.service.IElecRoleService;
import cn.itcast.elec.service.IElecSystemDDlService;
import cn.itcast.elec.util.XMLObject;
import cn.itcast.elec.web.form.ElecRoleForm;
import cn.itcast.elec.web.form.ElecSystemDDlForm;
import cn.itcast.elec.web.form.ElecUserForm;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ElecRoleAction extends BaseAction implements ModelDriven<ElecRoleForm>{

	private ElecRoleForm elecRoleForm = new ElecRoleForm();
	
	private IElecRoleService elecRoleService = (IElecRoleService) ServiceProvider.getService(IElecRoleService.SERVICE_NAME);
	
	private IElecSystemDDlService elecSystemDDlService = (IElecSystemDDlService) ServiceProvider.getService(IElecSystemDDlService.SERVICE_NAME);
	@Override
	public ElecRoleForm getModel() {
		// TODO Auto-generated method stub
		return elecRoleForm;
	}

	/**
	 * @Name:home
	 * @Description : 查询多有的角色类型（在数据字典中获取）
	 * 				  从Function.xml文件中查询系统所有的功能权限
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08
	 * @Parameters: null
	 * @return: String home 跳转到 roleIndex.jsp
	 */
	public String home(){
		//获取所有的角色类型
		List<ElecSystemDDlForm> systemList = elecSystemDDlService.findElecSystemDDLListByKeyWord("角色类型");
		request.setAttribute("systemList", systemList);
		//从Function.xml中获取权限的集合
		List<XMLObject> xmlList = elecRoleService.readXml();
		request.setAttribute("xmlList", xmlList);
		return "home";
	}

	/**
	 * @Name: edit
	 * @Description :1、 使用角色ID查询该角色下具有的权限，并与系统中所有的权限进行匹配
	 * 				 2、  使用角色ID查询该角色所拥有的用户
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08
	 * @Parameters: null
	 * @return: String edit 跳转到 roleEdit.jsp
	 */
	public String edit(){
		String roleID = elecRoleForm.getRole();
		//查询权限集合
		List<XMLObject> xmlList = elecRoleService.readEditXml(roleID);
		request.setAttribute("xmlList", xmlList);
		//查询用户集合
		List<ElecUserForm> userList = elecRoleService.findElecUserByRoleID(roleID);
		request.setAttribute("userList", userList);
		return "edit";
	}
	
	/**
	 * @Name: save
	 * @Description : 执行保存角色和权限的关联表
	 * 					保存用户和角色的关联表
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08
	 * @Parameters: null
	 * @return: String save 重定向到 roleIndex.jsp
	 */
	public String save(){
		elecRoleService.saveRole(elecRoleForm);
		return "save";
	}
}
