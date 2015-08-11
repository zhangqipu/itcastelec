package cn.itcast.elec.web.action;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecLogService;
import cn.itcast.elec.service.IElecSystemDDlService;
import cn.itcast.elec.service.IElecUserService;
import cn.itcast.elec.service.impl.ElecSystemDDlServiceImpl;
import cn.itcast.elec.web.form.ElecSystemDDlForm;
import cn.itcast.elec.web.form.ElecUserForm;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ElecUserAction extends BaseAction implements ModelDriven<ElecUserForm>{
	//调用菜单模块的业务层
	private ElecUserForm elecUserForm = new ElecUserForm();
	//调用用户登录模块的业务层
	private IElecUserService elecUserService = (IElecUserService) ServiceProvider.getService(IElecUserService.SERVICE_NAME);
	//调用数据字典的业务层
	private IElecSystemDDlService elecSystemDDlService = (IElecSystemDDlService) ServiceProvider.getService(IElecSystemDDlService.SERVICE_NAME);
	//调用日志管理的业务层
	private IElecLogService elecLogService = (IElecLogService) ServiceProvider.getService(IElecLogService.SERVICE_NAME);
	
	@Override
	public ElecUserForm getModel() {
		// TODO Auto-generated method stub
		return elecUserForm;
	}

	/**
	 * @Name:home
	 * @Description : 查询用户列表信息
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: null
	 * @return: String home 跳转到 UserIndex.jsp
	 */
	public String home(){
		List<ElecUserForm> list = elecUserService.findELecUserList(elecUserForm);
		request.setAttribute("userList", list);
		return "home";
	}
	
	/**
	 * @Name: add
	 * @Description : 跳转到添加用户的页面
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: null
	 * @return: String home 跳转到 UserAdd.jsp
	 */
	public String add(){
		//初始化数据字典
		this.initSystemDDl();
		
		return "add";
	}
	/**
	 * @Name: initSystemDDl
	 * @Description : 初始化新增和编辑用户页面中使用的数据字典
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: null
	 * @return: null
	 */	
	private void initSystemDDl() {
		/**
		 *使用数据类型进行查询，获取对应数据类型下的数据项编号和数据项名称
		 *查询性别、所属单位、是否在职 
		 */
		List<ElecSystemDDlForm> sexList = elecSystemDDlService.findElecSystemDDLListByKeyWord("性别");
		List<ElecSystemDDlForm> jctList = elecSystemDDlService.findElecSystemDDLListByKeyWord("所属单位");
		List<ElecSystemDDlForm> isDutyList = elecSystemDDlService.findElecSystemDDLListByKeyWord("是否在职");
		request.setAttribute("sexList", sexList);
		request.setAttribute("jctList", jctList);
		request.setAttribute("isdutyList", isDutyList);
	}

	/**
	 * @Name: save
	 * @Description : 保存用户信息
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: null
	 * @return: String save 跳转到 UserIndex.jsp
	 */
	public String save(){
		elecUserService.saveElecUser(elecUserForm);
		//2015-08-11 修改，将新增和修改的信息，添加日志中  begin
		ElecUser elecUser = (ElecUser)request.getSession().getAttribute("globle_user");
		if(elecUserForm.getUserID() != null && !elecUserForm.getUserID().equals("")){
			//修改保存
			elecLogService.saveElecLog(request, "用户管理："+elecUser.getUserName()+" 修改用户【"+elecUserForm.getUserName()+"】的信息");
		}else{
			//新增保存
			elecLogService.saveElecLog(request, "用户管理："+elecUser.getUserName()+" 新增用户【"+elecUserForm.getUserName()+"】的信息");
		}
		// end
		String roleflag = request.getParameter("roleflag");
		if(roleflag != null && roleflag.equals("1")){
			return edit();
		}
		return "list";
	}
	
	/**
	 * @Name: edit
	 * @Description : 编辑用户信息
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: null
	 * @return: String edit 跳转到 UserEdit.jsp
	 */
	public String edit(){
		elecUserForm = elecUserService.findELecUser(elecUserForm);
		//使用值栈的形式传递elecUserForm对象
//		ActionContext.getContext().getValueStack().push(elecUserForm);
		
		/**
		 * 使用ViewFlag字段
		 * 判断当前用户操作的是编辑页面 还是明细页面
		 * 如果viewflag==null:说明当前操作的是编辑页面
		 * 如果viewflag==1：说明当前操作的是明细页面
		 */
		String viewflag = elecUserForm.getViewflag();
		request.setAttribute("viewflag", viewflag);
		
		/**
		 * 2015-08-10修改
		 * 判断点击左侧“用户管理”链接
		 	*  用于判断当前操作者具有的角色是否是 系统管理员 、 高级管理员的标识。
		 	* 		如果当前操作者是系统管理员、高级管理员，则 点击 “用户管理” 时，
		 	* 		就跳转到userIndex.jsp，可以查看用户列表信息。
		 	* 		如果当前操作者不是系统管理员、高级管理员的时候，点击 “用户管理” 时，
		 	* 		就跳转到userEdit.jsp，可以对当前登录人进行编辑并保存
		 */
		String roleflag = elecUserForm.getRoleflag();
		request.setAttribute("roleflag", roleflag);
		   
		this.initSystemDDl();
		return "edit";
	}
	
	/**
	 * @Name: delete
	 * @Description : 删除用户信息
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-07
	 * @Parameters: null
	 * @return: String edit 跳转到 UserIndex.jsp
	 */
	public String delete(){
		elecUserService.deleteElecUser(elecUserForm);
		return "delete";
	}
}
