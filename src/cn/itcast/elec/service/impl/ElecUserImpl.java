package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecSystemDDlDao;
import cn.itcast.elec.dao.IElecUserDao;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecUserService;
import cn.itcast.elec.util.MD5keyBean;
import cn.itcast.elec.util.StringHelper;
import cn.itcast.elec.web.form.ElecUserForm;

/**
 * Spring默认Transactional事物管理机制如果程序抛出的是运行期例外，则数据回滚 
 * 事物处理如果是运行Exception例外，则数据不会滚。
 */
@Transactional(readOnly=true)
@Service(IElecUserService.SERVICE_NAME)  //把这个类交给spring容器去管理
public class ElecUserImpl implements IElecUserService{

	@Resource(name=IElecUserDao.SERVICE_NAME)
	private IElecUserDao elecUserDao;

	@Resource(name = IElecSystemDDlDao.SERVICE_NAME)
	private IElecSystemDDlDao elecSystemDDlDao;

	/**
	 * @Name: findELecUserList
	 * @Description :   查询用户列表信息
	 * 					判断用户姓名是否为空，如果不为空，按照用户姓名组织查询条件
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: null
	 * @return:List<ElecUserForm> 用户信息结果集对象
	 */
	@Override
	public List<ElecUserForm> findELecUserList(ElecUserForm elecUserForm) {
		//组织查询条件
		String hqlWhere = "";
		List<String> paramsList = new ArrayList<String>();
		if(elecUserForm != null && elecUserForm.getUserName() != null && !elecUserForm.getUserName().equals("")){
			hqlWhere += " and o.userName like ?";
			paramsList.add("%" + elecUserForm.getUserName() + "%");
		}
		Object[] params = paramsList.toArray();
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.onDutyDate", "desc");
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(hqlWhere, params, orderby);
		List<ElecUserForm> formList = this.elecUserPOListToVOList(list);

		return formList;
	}

	/**
	 * @Name: elecUserPOListToVOList
	 * @Description :   获取的用户列表中的值从PO对象转化为VO对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: List<ElecUser> list 存放PO对象
	 * @return:List<ElecUserForm> 存放VO对象
	 */
	private List<ElecUserForm> elecUserPOListToVOList(List<ElecUser> list) {
		List<ElecUserForm> formList = new ArrayList<ElecUserForm>();
		ElecUserForm elecUserForm = null;
		for(int i=0; list != null && i<list.size();i++){
			elecUserForm = new ElecUserForm();
			ElecUser elecUser = list.get(i);
			elecUserForm.setUserID(elecUser.getUserID());
			elecUserForm.setContactTel(elecUser.getContactTel());
			elecUserForm.setLogonName(elecUser.getLogonName());
			elecUserForm.setUserName(elecUser.getUserName());
			elecUserForm.setSexID(elecUser.getSexID() != null && !elecUser.getSexID().equals("") ? elecSystemDDlDao.findDDlName(elecUser.getSexID(),"性别"):"");
			elecUserForm.setIsDuty(elecUser.getIsDuty() != null && !elecUser.getIsDuty().equals("") ? elecSystemDDlDao.findDDlName(elecUser.getIsDuty(),"是否在职"):"");
			formList.add(elecUserForm);
		}
		return formList;
	}

	/**
	 * @Name: saveElecUser
	 * @Description :   保存用户信息
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: ElecUserForm elecUserForm  VO对象用于存放用户信息
	 * @return:null
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecUser(ElecUserForm elecUserForm) {
		//VO对象转换为PO对象
		ElecUser elecUser = this.elecUserVOToPO(elecUserForm);
		if(elecUserForm!=null && elecUserForm.getUserID()==null){
			elecUserDao.save(elecUser);
		}else{
			elecUserDao.update(elecUser);
		}
	}

	/**
	 * @Name: elecUserVOToPO
	 * @Description :   将用户信息从VO对象转换成PO对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: ElecUserForm elecUserForm  VO对象用于存放用户信息
	 * @return: ElecUser
	 */
	private ElecUser elecUserVOToPO(ElecUserForm elecUserForm) {
		ElecUser elecUser = new ElecUser();
		if(elecUserForm != null){
			if(elecUserForm != null && !elecUserForm.equals("")){
				elecUser.setUserID(elecUserForm.getUserID());
				if(elecUserForm.getOffDutyDate()!=null && !elecUserForm.getOffDutyDate().equals("")){
					elecUser.setOffDutyDate(StringHelper.stringConvertDate(elecUserForm.getOffDutyDate()));
				}
			}
			elecUser.setJctID(elecUserForm.getJctID());
			elecUser.setUserName(elecUserForm.getUserName());
			elecUser.setLogonName(elecUserForm.getLogonName());
			//2015-08-09 修改，使用MD5进行密码加密
			String password = elecUserForm.getLogonPwd();
			String md5password = "";
			//2015-08-09 修改，初始化密码 000000
			if(password == null || "".equals(password)){
				password = "000000";
			}
			String md5flag = elecUserForm.getMd5flag();
			//修改时2次密码一致，不需要进行密码加密
			if(md5flag != null && md5flag.equals("1")){
				md5password = password;
			}
			//无论是新增，还是处理修改（修改了当前密码），都需要进行密码加密
			else{
				MD5keyBean md5 = new MD5keyBean();
				md5password = md5.getkeyBeanofStr(password);
			}
			elecUser.setLogonPwd(md5password);
			//end
			//判断是否填写性别，即是否为空
			if(elecUserForm.getSexID() !=null && !elecUserForm.getSexID().equals(""))
				elecUser.setSexID(Integer.valueOf(elecUserForm.getSexID()));
			//判断是否填写出生日期，即是否为空
			if(elecUserForm.getBirthday() !=null && !elecUserForm.getBirthday().equals(""))
				elecUser.setBirthday(StringHelper.stringConvertDate(elecUserForm.getBirthday()));
			elecUser.setAddress(elecUserForm.getAddress());
			elecUser.setContactTel(elecUserForm.getContactTel());
			elecUser.setEmail(elecUserForm.getEmail());
			elecUser.setMobile(elecUserForm.getMobile());
			elecUser.setIsDuty(Integer.valueOf(elecUserForm.getIsDuty()));
			if(elecUserForm.getOnDutyDate() != null && !elecUserForm.getOnDutyDate().equals("")){
				elecUser.setOnDutyDate(StringHelper.stringConvertDate(elecUserForm.getOnDutyDate()));
			}
			elecUser.setRemark(elecUserForm.getRemark());
		}
		return elecUser;
	}

	/**
	 * @Name: findELecUser
	 * @Description :  使用用户ID进行查询，获取用户的详细信息
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: ElecUserForm elecUserForm  VO对象用于存放用户ID
	 * @return: ElecUserForm VO对象存放用户的详细信息
	 */
	@Override
	public ElecUserForm findELecUser(ElecUserForm elecUserForm) {
		String userID = elecUserForm.getUserID();
		ElecUser elecUser = elecUserDao.findObjectById(userID);
		//PO对象转换成PO对象
		elecUserForm = this.elecUserPOToVO(elecUser,elecUserForm);
		return elecUserForm;
	}

	/**
	 * @Name: elecUserPOToVO
	 * @Description : 获取用户的详细信息,从PO对象转换成VO对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: ElecUser elecUser  PO对象存放用户详细信息
	 * @return: ElecUserForm VO对象存放用户的详细信息
	 */
	private ElecUserForm elecUserPOToVO(ElecUser elecUser,ElecUserForm elecUserForm) {
		//		ElecUserForm elecUserForm = new ElecUserForm();
		if(elecUser != null){
			elecUserForm.setJctID(elecUser.getJctID());
			elecUserForm.setUserName(elecUser.getUserName());
			elecUserForm.setLogonName(elecUser.getLogonName());
			elecUserForm.setLogonPwd(elecUser.getLogonPwd());
			elecUserForm.setSexID(String.valueOf(elecUser.getSexID()));
			elecUserForm.setBirthday(String.valueOf(elecUser.getBirthday() !=null && !elecUser.getBirthday().equals("")?elecUser.getBirthday():""));
			elecUserForm.setAddress(elecUser.getAddress());
			elecUserForm.setContactTel(elecUser.getContactTel());
			elecUserForm.setEmail(elecUser.getEmail());
			elecUserForm.setMobile(elecUser.getMobile());
			elecUserForm.setIsDuty(String.valueOf(elecUser.getIsDuty()));
			elecUserForm.setOnDutyDate(String.valueOf(elecUser.getOnDutyDate() !=null && !elecUser.getOnDutyDate().equals("")?elecUser.getOnDutyDate():""));
			elecUserForm.setRemark(elecUser.getRemark());
		}
		return elecUserForm;
	}

	/**
	 * @Name: deleteElecUser
	 * @Description : 从页面中获取UserID的值，通过UserID来删除用户信息。
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-07
	 * @Parameters: ElecUserForm elecUserForm  VO对象存放用户ID
	 * @return: null
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteElecUser(ElecUserForm elecUserForm) {
		String userID = elecUserForm.getUserID();
		elecUserDao.deleteObjectByIds(userID);
	}

	/**
	 * @Name: checkLogonName
	 * @Description : 通过使用登录名查询数据库，作为条件判断当前登录名在数据库中是否存在。
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-07
	 * @Parameters: String logonName  登录名
	 * @return: String 		checkflag ：如果值为1，说明当前登录名在数据库中有重复记录，不能进行保存
	 *	 					checkflag : 如果只为2，说明当前登录名在数据库中灭有重复值，可以进行保存
	 */
	@Override
	public String checkLogonName(String logonName) {
		String hqlWhere = " and o.logonName = ?";
		Object [] params = {logonName};
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(hqlWhere, params, null);
		String checkflag = "";
		if(list !=null && list.size()>0){
			checkflag = "1";
		}else{
			checkflag = "2";
		}
		return checkflag;
	}
	/**
	 * @Name: findElecUserByLogonName
	 * @Description : 通过使用登录名获取用户的详细信息，用于首页登录的校验
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-10
	 * @Parameters: String name  登录名
	 * @return: ElecUser 	存放用户的详细信息	
	 */
	@Override
	public ElecUser findElecUserByLogonName(String name) {
		String hqlWhere = " and o.logonName = ?";
		Object [] params = {name};
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(hqlWhere, params, null);
		ElecUser elecUser = null;
		if(list != null && list.size()>0){
			elecUser = list.get(0);
		}
		return elecUser;
	}

	/**
	 * @Name: findElecPopedomByLogonName
	 * @Description : 使用登录名获取当前用户所具有的权限
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-10
	 * @Parameters: String name  登录名
	 * @return: String 	用户存放该用户具有的权限	
	 */
	@Override
	public String findElecPopedomByLogonName(String name) {
		List<Object> list = elecUserDao.findElecPopedomByLogonName(name);
		StringBuffer buffer = new StringBuffer("");
		for(int i = 0;list != null && i<list.size();i++){
			Object object = list.get(i);
			buffer.append(object.toString());
		}
		return buffer.toString();
	}

	/**
	 * @Name: findElecRoleByLogonName
	 * @Description : 使用登录名获取当前用户所具有的角色
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-10
	 * @Parameters: String name  登录名
	 * @return: Hashtable<String, String> 	存放角色的集合	
	 */
	@Override
	public Hashtable<String, String> findElecRoleByLogonName(String name) {
		List<Object []> list = elecUserDao.findElecRoleByLogonName(name);
		Hashtable<String, String> ht = null;
		if(list != null && list.size()>0){
			ht = new Hashtable<String, String>();
			for(int i=0;i<list.size();i++){
				Object[] object = list.get(i);
				ht.put(object[0].toString(), object[1].toString());
			}
		}
		return ht;
	}



}
