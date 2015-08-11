package cn.itcast.elec.web.action;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecCommonMsgService;
import cn.itcast.elec.service.IElecLogService;
import cn.itcast.elec.service.IElecUserService;
import cn.itcast.elec.service.impl.ElecLogServiceImpl;
import cn.itcast.elec.util.LogonUtils;
import cn.itcast.elec.util.MD5keyBean;
import cn.itcast.elec.web.form.ElecCommonMsgForm;
import cn.itcast.elec.web.form.ElecMenuForm;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ElecMenuAction extends BaseAction implements ModelDriven<ElecMenuForm>{
	//调用菜单模块的业务层
	private ElecMenuForm elecMenuForm = new ElecMenuForm();
	//调用用户登录模块的业务层
	private IElecUserService elecUserService = (IElecUserService)ServiceProvider.getService(IElecUserService.SERVICE_NAME);
	//调用待办事宜的业务层
	private IElecCommonMsgService elecCommonMsgService = (IElecCommonMsgService) ServiceProvider.getService(IElecCommonMsgService.SERVICE_NAME);
	//调用日志管理的业务层
	private IElecLogService elecLogService = (IElecLogService) ServiceProvider.getService(IElecLogService.SERVICE_NAME);
	//使用log4j
	private Log log = LogFactory.getLog(ElecMenuAction.class);
	
	@Override
	public ElecMenuForm getModel() {
		// TODO Auto-generated method stub
		return elecMenuForm;
	}

	/**
	 * @Name: home
	 * @Description :   从登录页面获取登录名和密码，验证是否合法
	 * 					如果合法，则验证成功，跳转到home.jsp
	 * 					如果不合法，则验证失败，回退到index.jsp
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-10
	 * @Parameters: null
	 * @return: String home 
							验证成功，跳转到home.jsp
							验证失败，回退到index.jsp
	 */
	public String home(){
		//2015-08-11,添加验证码的功能 begin
		boolean flag = LogonUtils.checkNumber(request);
		if(!flag){
			this.addFieldError("error", "验证码为空或者有误");
			return "error";
		}
		//end
		
		//获取当前登录名和密码
		String name = elecMenuForm.getName();
		String password = elecMenuForm.getPassword();
		MD5keyBean md5 = new MD5keyBean();
		String md5password = md5.getkeyBeanofStr(password);
		//使用登录名查询数据库，获取用户的详细信息
		ElecUser elecUser = elecUserService.findElecUserByLogonName(name);
		if(elecUser == null){
			this.addFieldError("error", "您当前的登录名不存在！");
			return "error";
		}    
		if(password == null || password.equals("") || !elecUser.getLogonPwd().equals(md5password)){
			this.addFieldError("error", "您当前输入的密码有误或不存在");
			return "error";
		}
		request.getSession().setAttribute("globle_user", elecUser);
		//获取当前登录名所具有的权限
		String popedom = elecUserService.findElecPopedomByLogonName(name);
		if(popedom == null || "".equals(popedom)){
			this.addFieldError("error", "当前的登录名没有分配权限，请于管理员联系！");
			return "error";
		}
		request.getSession().setAttribute("globle_popedom", popedom);
		//获取当前登录名所具有的角色
		Hashtable<String, String> ht = elecUserService.findElecRoleByLogonName(name);
		if(ht == null){
			this.addFieldError("error", "当前登录名没有分配角色，请于管理员联系");
			return "error";
		}
		request.getSession().setAttribute("globle_role", ht);
		//2015-08-11，添加记住我的功能，记住当前操作的用户名和密码  begin
		try {
			LogonUtils.remeberMeByCookie(request,response);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//end
		//2015-08-11,添加日志管理模块，维护系统的安全性能    begin
		/*使用log4j
		 *	java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
		 *	String d = date.toString();
		 *	log.info("登录名【"+elecUser.getUserName()+"】登录系统！时间是：" + d);
		 */
		//使用数据库表模式操作
		elecLogService.saveElecLog(request,"登录模块【"+elecUser.getUserName()+"】登录系统");
		//   end
		
		return "home";
	}
	
	public String title(){
		
		return "title";
	}
	
	public String left(){
		
		return "left";
	}
	
	public String change1(){
		
		return "change1";
	}
	
	public String loading(){
		
		return "loading";
	}
	
	public String alermJX(){
		
		return "alermJX";
	}
	
	/**
	 * @Name:alermSB
	 * @Description : 查询当天的设备运行情况
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: 无
	 * @return:String alermSB  跳转到alermSB.jsp
	 */
	public String alermSB(){
		List<ElecCommonMsgForm> list = elecCommonMsgService.findElecCommonMsgListByCurrentDate();
		request.setAttribute("commonList", list);
		return "alermSB";
	}
	
	public String alermXZ(){
		
		return "alermXZ";
	}
	
	public String alermYS(){
		
		return "alermYS";
	}
	
	/**
	 * @Name:alermZD
	 * @Description : 查询当天的站点运行情况
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: 无
	 * @return:String alermZD  跳转到alermZD.jsp
	 */
	public String alermZD(){
		List<ElecCommonMsgForm> list = elecCommonMsgService.findElecCommonMsgListByCurrentDate();
		request.setAttribute("commonList", list);
		return "alermZD";
	}
	
	/**
	 * @Name: logout
	 * @Description : 重新回退到首页
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-10
	 * @Parameters: 无
	 * @return:String logout  跳转到index.jsp
	 */
	public String logout(){
		//清空session
		request.getSession().invalidate();
		return "logout";
	}
	
}
