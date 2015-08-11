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
import cn.itcast.elec.dao.IElecSystemDDlDao;
import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.domain.ElecSystemDDl;
import cn.itcast.elec.service.IElecCommonMsgService;
import cn.itcast.elec.service.IElecSystemDDlService;
import cn.itcast.elec.web.form.ElecCommonMsgForm;
import cn.itcast.elec.web.form.ElecSystemDDlForm;

/**
 * Spring默认Transactional事物管理机制如果程序抛出的是运行期例外，则数据回滚 
 * 事物处理如果是运行Exception例外，则数据不会滚。
 */
@Transactional(readOnly=true)
@Service(IElecSystemDDlService.SERVICE_NAME)  //把这个类交给spring容器去管理
public class ElecSystemDDlServiceImpl implements IElecSystemDDlService{

	@Resource(name=IElecSystemDDlDao.SERVICE_NAME)
	private IElecSystemDDlDao elecSystemDDlDao;


	/**
	 * @Name:findKeyWord
	 * @Description : 查询数据类型关键字，且去掉重复值
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: null
	 * @return:List<ElecSystemDDlForm> 数据类型列表
	 */
	@Override
	public List<ElecSystemDDlForm> findKeyWord() {
		List<Object> list = elecSystemDDlDao.findKeyWord();
		List<ElecSystemDDlForm> formList = this.elecSystemDDlObjectToVO(list);
		return formList;
	}

	/**
	 * @Name:elecSystemDDlObjectToVO
	 * @Description : 将查询的数据类型列表由Object 对象转换成VO对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: List<Object> list Object集合
	 * @return:List<ElecSystemDDlForm> VO对象
	 */
	private List<ElecSystemDDlForm> elecSystemDDlObjectToVO(List<Object> list) {
		List<ElecSystemDDlForm> formList = new ArrayList<ElecSystemDDlForm>();
		ElecSystemDDlForm elecSystemDDlForm = null;
		for(int i = 0 ;list != null && i<list.size();i++){
			Object object = list.get(i);
			elecSystemDDlForm = new ElecSystemDDlForm();
			elecSystemDDlForm.setKeyword(object.toString());
			formList.add(elecSystemDDlForm);
		}
		return formList;
	}

	/**
	 * @Name:findElecSystemDDLListByKeyWord
	 * @Description : 根据选中数据类型，查询对应的数据项
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: String keyWord VO的属性
	 * @return:List<ElecSystemDDlForm> 对应数据项的VO集合
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public List<ElecSystemDDlForm> findElecSystemDDLListByKeyWord(String keyword) {
		List<ElecSystemDDl> list = this.findSystemDDlListByCondition(keyword);
		List<ElecSystemDDlForm> formList = this.elecSystemDDlPOListToVOList(list);
		return formList;
	}

	/**
	 * @Name: findSystemDDlListByCondition
	 * @Description : 根据选中数据类型，查询对应的数据项,获取数据项的集合列表
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-06
	 * @Parameters: String keyword 数据类型
	 * @return:List<ElecSystemDDl> 对应数据项的VO集合
	 */
	private List<ElecSystemDDl> findSystemDDlListByCondition(String keyword) {
		String hqlWhere = " and o.keyword = ?";
		Object [] params = {keyword};
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.ddlCode", "asc");
		List<ElecSystemDDl> list = elecSystemDDlDao.findCollectionByConditionNoPage(hqlWhere, params, orderby);
		return list;
	}

	/**
	 * @Name: elecSystemDDlPOListToVOList
	 * @Description : 数据项的集合列表从PO对象转化为VO对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: List<ElecSystemDDl> list 存放PO集合
	 * @return:List<ElecSystemDDlForm> 对应数据项的VO集合
	 */
	private List<ElecSystemDDlForm> elecSystemDDlPOListToVOList(
			List<ElecSystemDDl> list) {
		List<ElecSystemDDlForm> formList = new ArrayList<ElecSystemDDlForm>();
		ElecSystemDDlForm elecSystemDDlForm = null;
		for(int i = 0;list !=null && i<list.size();i++){
			ElecSystemDDl elecSystemDDl = list.get(i);
			elecSystemDDlForm = new ElecSystemDDlForm();
			elecSystemDDlForm.setSeqID(String.valueOf(elecSystemDDl.getSeqID()));
			elecSystemDDlForm.setKeyword(elecSystemDDl.getKeyword());
			elecSystemDDlForm.setDdlCode(String.valueOf(elecSystemDDl.getDdlCode()));
			elecSystemDDlForm.setDdlName(elecSystemDDl.getDdlName());
			formList.add(elecSystemDDlForm);
		}
		return formList;
	}

	/**
	 * @Name: saveElecSystemDDl
	 * @Description : 保存数据字典，保存时包括数据类型，数据项编号、数据项名称
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: ElecSystemDDlForm elecSystemDDlForm 存放页面传递的表单值
	 * @return:null
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecSystemDDl(ElecSystemDDlForm elecSystemDDlForm) {
		//获取页面传递的表单值
		//获取数据类型
		String keyword = elecSystemDDlForm.getKeywordname();
		//获取判断新增是新增数据类型还是在原有的数据类型中编辑的标识
		String typeflag = elecSystemDDlForm.getTypeflag();
		//获取需要保存的数据项的名称
		String [] itemname = elecSystemDDlForm.getItemname();
		//如果是新增数据类型的操作，此时typeflag==new
		
		System.out.println(keyword+"..."+typeflag+"..."+itemname[0]+"...");
		
		if(typeflag != null && typeflag.equals("new")){
			this.saveSystemDDlWithParams(keyword,itemname);
		}
		//否则是表示在原有的数据类型中进行修改和编辑，此时typeflag==and
		else{
			List<ElecSystemDDl> list = findSystemDDlListByCondition(keyword);
			elecSystemDDlDao.deleteObjectByCollection(list);
			//保存数据字典
			this.saveSystemDDlWithParams(keyword, itemname);
		}
		
	}

	/**
	 * @Name: saveSystemDDlWithParams
	 * @Description : 通过页面传递的参数保存数据字典
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: String keyWord    数据类型
	 * 				String[] itemname 数据项名称
	 * @return:null
	 */
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	private void saveSystemDDlWithParams(String keyWord, String[] itemname) {
		List<ElecSystemDDl> list = new ArrayList<ElecSystemDDl>();
		for(int i = 0; itemname !=null && i<itemname.length; i++){
			ElecSystemDDl elecSystemDDl = new ElecSystemDDl();
			elecSystemDDl.setKeyword(keyWord);
			elecSystemDDl.setDdlName(itemname[i]);
			elecSystemDDl.setDdlCode(new Integer(i+1));
			list.add(elecSystemDDl);
//			elecSystemDDlDao.save(elecSystemDDl);
		}
		elecSystemDDlDao.saveObjectByCollection(list);
	}


}
