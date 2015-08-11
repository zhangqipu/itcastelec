package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.service.IElecTextService;
import cn.itcast.elec.web.form.ElecTextForm;

@Transactional(readOnly=true)
/**
 * Spring默认Transactional事物管理机制如果程序抛出的是运行期例外，则数据回滚 
 * 事物处理如果是运行Exception例外，则数据不会滚。
 */
@Service(IElecTextService.SERVICE_NAME)  //把这个类交给spring容器去管理
public class ElecTextServiceImpl implements IElecTextService{

	@Resource(name=IElecTextDao.SERVICE_NAME)
	private IElecTextDao elecTextDao;
	
	
	/**
	 * @Name:saveElecText
	 * @Description : 通过传入一个对象来进行保存对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters:ElecText elecText 实体
	 * @return:返回 null
	 */
	@Override
	@Transactional(readOnly=false,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public void saveElecText(ElecText elecText) {
		elecTextDao.save(elecText);
	}

	/**
	 * @Name:findCollectionByConditionNoPage
	 * @Description : 使用查询条件查询列表的集合（不分页）
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: ElecTextForm elecTextForm VO对象
	 * @return:List<ElecText> 列表集合
	 */
	@Override
	public List<ElecText> findCollectionByConditionNoPage(ElecTextForm elecTextForm) {
		/**
		 * 组织HQL语句的where条件
		 * 
		 * select * from elec_text o where 1=1		//放置DAO层
		 * where o.textName like "%hello%"			//放置Service层
		 * and o.textRemark like "%haha%"
		 * order by o.textDate desc , o.textName asc;
		 */
		String hqlWhere = "";
		List<String> paramsList = new ArrayList<String>();
		if(elecTextForm != null && StringUtils.isNotBlank(elecTextForm.getTextName())){
			hqlWhere += " and o.textName like ?";
			paramsList.add("%"+elecTextForm.getTextName()+"%");
		}
		if(elecTextForm != null && StringUtils.isNotBlank(elecTextForm.getTextRemark())){
			hqlWhere += " and o.textRemark like ?";
			paramsList.add("%"+elecTextForm.getTextRemark()+"%");
		}
		//把list集合变成一个数组
		Object [] params = paramsList.toArray();
		/**
		 * 组织排序语句
		 * 			order by o.textDate desc , o.textName asc;
		 */
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.textDate", "desc");
		orderby.put("o.textName", "asc");
		//查询列表
		List<ElecText> list = elecTextDao.findCollectionByConditionNoPage(hqlWhere,params,orderby);
		for(int i=0;list != null && i<list.size();i++){
			ElecText elecText = list.get(i);
			System.out.println(elecText.getTextName()+"      "+elecText.getTextRemark());
		}
		return list;
	}

}
