package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecLogDao;
import cn.itcast.elec.domain.ElecLog;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecLogService;
import cn.itcast.elec.web.form.ElecLogForm;

/**
 * Spring默认Transactional事物管理机制如果程序抛出的是运行期例外，则数据回滚 
 * 事物处理如果是运行Exception例外，则数据不会滚。
 */
@Transactional(readOnly=true)
@Service(IElecLogService.SERVICE_NAME)  //把这个类交给spring容器去管理
public class ElecLogServiceImpl implements IElecLogService{

	@Resource(name=IElecLogDao.SERVICE_NAME)
	private IElecLogDao elecLogDao;

	/**
	 * @Name: saveElecLog
	 * @Description : 保存日志信息 
	 * @author ： 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-11
	 * @Parameters: HttpServletRequest request 用于保存用户等相关信息
 					String string  操作明细
	 * @return: null
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecLog(HttpServletRequest request, String details) {
		ElecLog elecLog = new ElecLog();
		elecLog.setIpAddress(request.getRemoteAddr());//IP地址

		ElecUser elecUser = (ElecUser) request.getSession().getAttribute("globle_user");
		elecLog.setOpeName(elecUser.getUserName());//操作者
		elecLog.setOpeTime(new Date());//操作时间
		elecLog.setDetails(details);
		elecLogDao.save(elecLog);
	}

	/**
	 * @Name: findElecLogListByCondition
	 * @Description : 使用查询条件，查询日志信息列表
	 * @author ： 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-11
	 * @Parameters: ElecLogForm elecLogForm 用于存放操作人信息
	 * @return: List<ElecLogForm> 日志信息列表
	 */
	@Override
	public List<ElecLogForm> findElecLogListByCondition(ElecLogForm elecLogForm) {
		//组织查询和排序的条件
		String hqlWhere = ""; 
		List<String> paramsList = new ArrayList<String>();
		if(elecLogForm != null && elecLogForm.getOpeName() != null && !elecLogForm.getOpeName().equals("")){
			hqlWhere += "and o.OpeName like ?";
			paramsList.add("%"+elecLogForm.getOpeName()+"%");
		}
		Object [] params = paramsList.toArray();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.OpeName", "desc");
		List<ElecLog> list = elecLogDao.findCollectionByConditionNoPage(hqlWhere, params, orderby);
		//PO对象的结果集转换成VO对象的结果集
		List<ElecLogForm> formList = this.elecLogPOListToVOList(list);
		return formList;
	}

	/**
	 * @Name: elecLogPOListToVOList
	 * @Description : 日志信息列表中，PO对象的结果集转换成VO对象的结果集
	 * @author ： 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-11
	 * @Parameters: List<ElecLog> list PO对象集合
	 * @return: List<ElecLogForm> VO对象集合
	 */
	private List<ElecLogForm> elecLogPOListToVOList(List<ElecLog> list) {
		List<ElecLogForm> formList = new ArrayList<ElecLogForm>();
		ElecLogForm elecLogForm = null;
		for(int i = 0;list !=null && i < list.size();i++){
			ElecLog elecLog = list.get(i);
			elecLogForm = new ElecLogForm();
			elecLogForm.setLogID(elecLog.getLogID());
			elecLogForm.setOpeName(elecLog.getOpeName());
			elecLogForm.setDetails(elecLog.getDetails());
			elecLogForm.setOpeTime(String.valueOf(elecLog.getOpeTime() != null ? elecLog.getOpeTime() : ""));
			elecLogForm.setIpAddress(elecLog.getIpAddress());
			formList.add(elecLogForm);
		}
		return formList;
	}

	/**
	 * @Name: deleteElecLogByLogIDs
	 * @Description : 使用日志ID，删除日志列表信息
	 * @author ： 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-16
	 * @Parameters: ElecLogForm elecLogForm 用于存放日志ID的数组
	 * @return: null
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteElecLogByLogIDs(ElecLogForm elecLogForm) {
		//第一种方式
		// String [] logids = elecLogForm.getLogid();
		// elecLogDao.deleteObjectByIds(logids);
		//第二种方式
		String logID = elecLogForm.getLogID();
		String [] logids = logID.split(",");
		for(int i = 0;logids != null && i<logids.length;i++){
			String logid = logids[i];
			logids[i] = logid.trim();
		}

		elecLogDao.deleteObjectByIds(logids);

	}
}
