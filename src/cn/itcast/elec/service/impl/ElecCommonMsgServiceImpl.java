package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecCommonMsgDao;
import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.service.IElecCommonMsgService;
import cn.itcast.elec.web.form.ElecCommonMsgForm;

@Transactional(readOnly=true)
/**
 * Spring默认Transactional事物管理机制如果程序抛出的是运行期例外，则数据回滚 
 * 事物处理如果是运行Exception例外，则数据不会滚。
 */
@Service(IElecCommonMsgService.SERVICE_NAME)  //把这个类交给spring容器去管理
public class ElecCommonMsgServiceImpl implements IElecCommonMsgService{

	@Resource(name=IElecCommonMsgDao.SERVICE_NAME)
	private IElecCommonMsgDao elecCommonMsgDao;

	/**
	 * @Name:findElecCommonMsgList
	 * @Description : 查询所有的待办事宜
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: null
	 * @return:List<ElecCommonMsgForm> 待办事宜结果集列表
	 */
	@Override
	public List<ElecCommonMsgForm> findElecCommonMsgList() {
		String hqlWhere = "";
		Object [] params = null;
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put(" o.createDate", "desc");
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage(hqlWhere, params, orderby);
		List<ElecCommonMsgForm> formList = this.elecCommonMsgPOListToVOList(list);
		return formList;
	}

	/**
	 * @Name:elecCommonMsgPOListToVOList
	 * @Description : 将待办事宜的结果信息，从PO对象转换为VO对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: List<ElecCommonMsg> list PO对象结果集
	 * @return:List<ElecCommonMsgForm> VO对象结果集
	 */
	private List<ElecCommonMsgForm> elecCommonMsgPOListToVOList(
			List<ElecCommonMsg> list) {
		
		List<ElecCommonMsgForm> formlist = new ArrayList<ElecCommonMsgForm>();
		ElecCommonMsgForm elecCommonMsgForm = null;
		for(int i=0;list !=null && i<list.size();i++){
			ElecCommonMsg elecCommonMsg = list.get(i);
			elecCommonMsgForm = new ElecCommonMsgForm();
			elecCommonMsgForm.setComID(elecCommonMsg.getComID());
			elecCommonMsgForm.setStationRun(elecCommonMsg.getStationRun());
			elecCommonMsgForm.setDevRun(elecCommonMsg.getDevRun());
			elecCommonMsgForm.setCreateDate(String.valueOf(elecCommonMsg.getCreateDate()!=null?elecCommonMsg.getCreateDate():""));
			formlist.add(elecCommonMsgForm);
		}
		return formlist;
	}

	/**
	 * @Name:saveElecCommonMsg
	 * @Description : 保存待办事宜信息
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: ElecCommonMsgForm elecCommonMsgForm VO对象，页面传递的参数值
	 * @return: String save 重定向到actingIndex.jsp
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecCommonMsg(ElecCommonMsgForm elecCommonMsgForm) {
		//VO对象转换成PO对象
		ElecCommonMsg elecCommonMsg = this.elecCommonMsgVOToPO(elecCommonMsgForm);
		elecCommonMsgDao.save(elecCommonMsg);
		
	}

	/**
	 * @Name:elecCommonMsgVOToPO
	 * @Description : 页面传递的代办事宜信息从VO对象转换成PO对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: ElecCommonMsgForm elecCommonMsgForm VO对象，页面传递的参数值
	 * @return: ElecCommonMsg  PO对象
	 */
	private ElecCommonMsg elecCommonMsgVOToPO(
			ElecCommonMsgForm elecCommonMsgForm) {
		ElecCommonMsg elecCommonMsg = new ElecCommonMsg();
		if(elecCommonMsgForm != null){
			elecCommonMsg.setStationRun(elecCommonMsgForm.getStationRun());
			elecCommonMsg.setDevRun(elecCommonMsgForm.getDevRun());
			elecCommonMsg.setCreateDate(new Date());
		}
		return elecCommonMsg;
	}

	/**
	 * @Name:findElecCommonMsgListByCurrentDate
	 * @Description : 通过当前日期查询待办事宜列表
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: null
	 * @return: List<ElecCommonMsgForm> 代办事宜列表
	 */
	@Override
	public List<ElecCommonMsgForm> findElecCommonMsgListByCurrentDate() {
		//获取当前日期yyyy-MM-dd
		java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
		String currentDate = date.toString();
		List<Object[]> list = elecCommonMsgDao.findElecCommonMsgListByCurrentDate(currentDate);
		List<ElecCommonMsgForm> formList = this.elecCommonMsgObjectListToVOList(list);
		return formList;
	}

	/**
	 * @Name:elecCommonMsgObjectListToVOList
	 * @Description : 将待办事宜查询的结果由Object[]转换成VO对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: List<Object[]> list 存放Object[]数组对象
	 * @return: List<ElecCommonMsgForm> VO对象
	 */
	private List<ElecCommonMsgForm> elecCommonMsgObjectListToVOList(
			List<Object[]> list) {
		List<ElecCommonMsgForm> formList = new ArrayList<ElecCommonMsgForm>();
		ElecCommonMsgForm elecCommonMsgForm = null;
		for(int i=0;list != null && i<list.size();i++){
			Object[] object = list.get(i);
			elecCommonMsgForm = new ElecCommonMsgForm();
			elecCommonMsgForm.setStationRun(object[0].toString());
			elecCommonMsgForm.setDevRun(object[1].toString());
			formList.add(elecCommonMsgForm);
		}
		return formList;
	}
	
	
	
	

}
