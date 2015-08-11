package cn.itcast.elec.web.action;

import java.util.List;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.service.IElecSystemDDlService;
import cn.itcast.elec.web.form.ElecSystemDDlForm;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ElecSystemDDlAction extends BaseAction implements ModelDriven<ElecSystemDDlForm>{

	private ElecSystemDDlForm elecSystemDDlForm = new ElecSystemDDlForm();
	
	private IElecSystemDDlService elecSystemDDlService = (IElecSystemDDlService) ServiceProvider.getService(IElecSystemDDlService.SERVICE_NAME);
	
	@Override
	public ElecSystemDDlForm getModel() {
		// TODO Auto-generated method stub
		return elecSystemDDlForm;
	}

	/**
	 * @Name:home
	 * @Description : 查询数据类型，并去掉重复值 
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: null
	 * @return: String home 跳转到 dictionaryIndex.jsp
	 */
	public String home(){
		List<ElecSystemDDlForm> list = elecSystemDDlService.findKeyWord();
		request.setAttribute("systemList", list);
		return "home";
	}
	
	/**
	 * @Name:edit
	 * @Description : 根据选中数据类型，跳转到编辑此数据类型的页面
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: null
	 * @return: String edit 跳转到 dictionaryEdit.jsp
	 */
	public String edit(){
		//获取数据类型
		String keyWord = elecSystemDDlForm.getKeyword();
		List<ElecSystemDDlForm> list = elecSystemDDlService.findElecSystemDDLListByKeyWord(keyWord);
		request.setAttribute("systemList", list);
		return "edit";
	}
	
	/**
	 * @Name:save
	 * @Description : 保存数据字典
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-04
	 * @Parameters: null
	 * @return: String save 重定向到 dictionaryIndex.jsp
	 */
	public String save(){
		System.out.println("////////////////////////");
		elecSystemDDlService.saveElecSystemDDl(elecSystemDDlForm);
		return "save";
	}
}
