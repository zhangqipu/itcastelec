package cn.itcast.elec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecRolePopedomDao;
import cn.itcast.elec.dao.IElecUserRoleDao;
import cn.itcast.elec.domain.ElecRolePopedom;
import cn.itcast.elec.domain.ElecUserRole;
import cn.itcast.elec.service.IElecRoleService;
import cn.itcast.elec.util.XMLObject;
import cn.itcast.elec.web.form.ElecRoleForm;
import cn.itcast.elec.web.form.ElecUserForm;

/**
 * Spring默认Transactional事物管理机制如果程序抛出的是运行期例外，则数据回滚 
 * 事物处理如果是运行Exception例外，则数据不会滚。
 */
@Transactional(readOnly=true)
@Service(IElecRoleService.SERVICE_NAME)  //把这个类交给spring容器去管理
public class ElecRoleServiceImpl implements IElecRoleService{

	@Resource(name=IElecUserRoleDao.SERVICE_NAME)
	private IElecUserRoleDao elecUserRoleDao;
	
	@Resource(name = IElecRolePopedomDao.SERVICE_NAME)
	private IElecRolePopedomDao elecRolePopedomDao;

	/**
	 * @Name: readXml
	 * @Description :   从Function.xml文件中查询系统的所有的功能权限
	 * 					存放到XMLObject对象中
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08
	 * @Parameters: null
	 * @return:List<ElecUserForm> 用户信息结果集对象
	 */
	@Override
	public List<XMLObject> readXml() {
		//File f = new File("H:\\apache-tomcat-7.0.55\\me-webapps\\itcast1222elec\\WEB-INF\\classes\\Function.xml");
		ServletContext servletContext = ServletActionContext.getServletContext();
		String realpath = servletContext.getRealPath("/WEB-INF/classes/Function.xml");
		File f = new File(realpath);
		List<XMLObject> xmlList = new ArrayList<XMLObject>();
		//使用dom4j读取配置文件
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(realpath);
			Element element = document.getRootElement();
			XMLObject xmlObject = null;
			/**
			 * Function对应配置文件中的Function元素节点
			 * FunctionCode对应配置文件中Function元素节点下的FunctionCode元素节点
			 * FunctionName对应配置文件中Function元素节点下的FunctionName元素节点
			 * ParentCode对应配置文件中Function元素节点下的ParentCode元素节点
			 * ParentName对应配置文件中Function元素节点下的ParentName元素节点
			 */
			for(Iterator<Element> iter = element.elementIterator("Function");iter.hasNext();){
				Element xmlElement = iter.next();
				xmlObject = new XMLObject();
				xmlObject.setCode(xmlElement.elementText("FunctionCode"));
				xmlObject.setName(xmlElement.elementText("FunctionName"));
				xmlObject.setParentCode(xmlElement.elementText("ParentCode"));
				xmlObject.setParentName(xmlElement.elementText("ParentName"));
				xmlList.add(xmlObject);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return xmlList;
	}

	/**
	 * @Name: readEditXml
	 * @Description :   使用角色ID查询该角色下具有的权限，并与系统中所有的权限进行匹配
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08 （创建日期）
	 * @Parameters: String roleID 角色ID
	 * @return: List<XMLObject> 权限集合（匹配完成）
	 */
	@Override
	public List<XMLObject> readEditXml(String roleID) {
		// 使用roleID查询该角色下具有的权限
		ElecRolePopedom elecRolePopedom = elecRolePopedomDao.findObjectById(roleID);
		String popedom = "";
		if(elecRolePopedom != null){
			popedom = elecRolePopedom.getPopedomcode();
		}
		//与系统中所有的权限进行匹配
		List<XMLObject> list = this.readXmlByPopedom(popedom);
		return list;
	}

	/**
	 * @Name: readXmlByPopedom
	 * @Description :  读取配置文件，获取系统中的所有权限，与当前中所有的权限进行匹配
	 * 				   如果匹配不成功，   flag = 0；表示该角色不具有权限，则页面中权限复选框不被选中
	 *               如果匹配成功，flag = 1，表示该角色具有此权限，则页面中权限复选框被选中
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08 （创建日期）
	 * @Parameters: String popedom 角色ID
	 * @return: List<XMLObject> 权限集合（匹配完成）
	 */
	private List<XMLObject> readXmlByPopedom(String popedom) {
		List<XMLObject> list = new ArrayList<XMLObject>();
		List<XMLObject> xmlList = this.readXml();
		for(int i=0;xmlList != null && i<xmlList.size();i++){
			XMLObject object = xmlList.get(i);
			//表示当前权限被选中
			if(popedom.contains(object.getCode())){
				object.setFlag("1");
			}else{
				object.setFlag("0");
			}
			list.add(object);
		}
		return list;
	}

	/**
	 * @Name: findElecUserByRoleID
	 * @Description :  查询用户列表集合，获取系统中的所有的用户，并与该角色具有的用户进行匹配
	 * 				        如果匹配不成功，   flag = 0；表示该角色不拥有的用户，则页面中用户复选框不被选中
	 *                 如果匹配成功，flag = 1，表示该角色拥有此用户，则页面中用户复选框被选中
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08 （创建日期）
	 * @Parameters: String roleID 角色ID
	 * @return: ist<ElecUserForm> 用户集合（匹配完成）
	 */
	@Override
	public List<ElecUserForm> findElecUserByRoleID(String roleID) {
		List<Object []> list = elecUserRoleDao.findElecUserListByRoleID(roleID); 
		List<ElecUserForm> formList = this.elecUserRoleObjectListToVOList(list);
		return formList;
	}

	/**
	 * @Name: elecUserRoleObjectListToVOList
	 * @Description :  将获取到的用户列表信息从Object对象转换成VO对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08 （创建日期）
	 * @Parameters: List<Object[]> list PO对象集合
	 * @return: ist<ElecUserForm> VO对象集合（匹配完成）
	 */
	private List<ElecUserForm> elecUserRoleObjectListToVOList(
			List<Object[]> list) {
		List<ElecUserForm> formList = new ArrayList<ElecUserForm>();
		ElecUserForm elecUserForm = null;
		for(int i=0;list != null && i<list.size();i++){
			Object [] objects = list.get(i);
			elecUserForm = new ElecUserForm();
			elecUserForm.setFlag(objects[0].toString());
			elecUserForm.setUserID(objects[1].toString());
			elecUserForm.setUserName(objects[2].toString());
			elecUserForm.setLogonName(objects[3].toString());
			formList.add(elecUserForm);
		}
		return formList;
	}

	/**
	 * @Name: saveRole
	 * @Description :  保存角色和权限的关联表  && 保存用户和角色的关联表
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08 （创建日期）
	 * @Parameters: ElecRoleForm elecRoleForm VO对象，存放角色ID、权限code数组、用户ID数组
	 * @return: null
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveRole(ElecRoleForm elecRoleForm) {
		// 保存角色和权限的关联表
		this.saveRolePopedom(elecRoleForm);
		// 保存用户和角色的关联表
		this.saveUserRole(elecRoleForm);
		
	}

	/**
	 * @Name: saveUserRole
	 * @Description :  保存用户和角色的关联表
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08 （创建日期）
	 * @Parameters: ElecRoleForm elecRoleForm VO对象，存放角色ID、权限code数组、用户ID数组
	 * @return: null
	 */
	private void saveUserRole(ElecRoleForm elecRoleForm) {
		//角色ID
		String roleid = elecRoleForm.getRoleid();
		//用户ID数组
		String [] selectuser = elecRoleForm.getSelectuser();
		/**
		 * 以roleID作为条件，查询用户和角色的关联表，获取用户和角色关联的集合对象
		 */
		String hqlWhere = " and o.roleID = ? ";
		Object [] params = {roleid};
		List<ElecUserRole> entities = elecUserRoleDao.findCollectionByConditionNoPage(hqlWhere, params, null);
		/**
		 * 以roleID为条件，删除用户和角色的关联表
		 */
		elecUserRoleDao.deleteObjectByCollection(entities);
		//新增用户和角色的关联表
		List<ElecUserRole> list = new ArrayList<ElecUserRole>();
		for(int i=0;selectuser != null && i<selectuser.length;i++){
			String userID = selectuser[i];
			ElecUserRole elecUserRole = new ElecUserRole();
			elecUserRole.setRoleID(roleid);
			elecUserRole.setUserID(userID);
			list.add(elecUserRole);
//			elecUserRoleDao.save(elecUserRole);
		}
		elecUserRoleDao.saveObjectByCollection(list);
	}

	/**
	 * @Name: saveRolePopedom
	 * @Description :   保存角色和权限的关联表
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-08 （创建日期）
	 * @Parameters: ElecRoleForm elecRoleForm VO对象，存放角色ID、权限code数组、用户ID数组
	 * @return: null
	 */
	private void saveRolePopedom(ElecRoleForm elecRoleForm) {
		// 角色ID
		String roleid = elecRoleForm.getRoleid();
		//权限code集合
		String [] selectoper = elecRoleForm.getSelectoper();
		StringBuffer popedom = new StringBuffer("");
		for(int i = 0;selectoper != null && i<selectoper.length;i++){
			popedom.append(selectoper[i]);
		}
		//使用角色ID查询角色和权限的关联表
		ElecRolePopedom elecRolePopedom = elecRolePopedomDao.findObjectById(roleid);
		//说明角色和权限关联表中存在该角色的记录，此时执行update的操作
		if(elecRolePopedom != null){
			elecRolePopedom.setPopedomcode(popedom.toString());
			elecRolePopedomDao.update(elecRolePopedom);
		}
		//说明角色和权限关联表中不存在该角色的记录，此时执行save的操作
		else{
			elecRolePopedom = new ElecRolePopedom();
			elecRolePopedom.setRoleID(roleid);
			elecRolePopedom.setPopedomcode(popedom.toString());
			elecRolePopedomDao.save(elecRolePopedom);
		}
	}	
	
}
