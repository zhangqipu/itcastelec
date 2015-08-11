package cn.itcast.elec.web.action;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.service.IElecTextService;
import cn.itcast.elec.util.StringHelper;
import cn.itcast.elec.web.form.ElecTextForm;

import com.opensymphony.xwork2.ModelDriven;

public class ElecTextAction extends BaseAction implements ModelDriven<ElecTextForm> {

	private ElecTextForm elecTextForm = new ElecTextForm();
	private HttpServletRequest request = null;
	private IElecTextService elecTextService = (IElecTextService) ServiceProvider.getService(IElecTextService.SERVICE_NAME);

	@Override
	public ElecTextForm getModel() {
		return elecTextForm;
	}
	/**
	 * @Name:save
	 * @Description : 
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：
	 * @Parameters:
	 * @return:
	 */
	
	public String save(){
		//VO对象转换成PO对象
		
		//实例化PO对象
		ElecText elecText = new ElecText();
		//测试名称
		elecText.setTextName(elecTextForm.getTextName());
		//测试日期
		elecText.setTextDate(StringHelper.stringConvertDate(elecTextForm.getTextDate()));
		//测试备注
		elecText.setTextRemark(elecTextForm.getTextRemark());

		elecTextService.saveElecText(elecText);
		return "save";
	}

}
