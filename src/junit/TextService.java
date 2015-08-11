package junit;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.service.IElecTextService;
import cn.itcast.elec.web.form.ElecTextForm;

public class TextService {

	@Test
	public void saveElecText(){
		IElecTextService elecTextService = (IElecTextService) ServiceProvider.getService(IElecTextService.SERVICE_NAME);
		//实例化PO对象，赋值，执行保存
		ElecText elecText = new ElecText();
		elecText.setTextDate(new Date());
		elecText.setTextName("测试Service名称123");
		elecText.setTextRemark("测试Service备注123");
		elecTextService.saveElecText(elecText);
	}
	/**
	 * 通过查询条件，查询对象的列表集合
	 * 模仿Action层
	 */
	@Test
	public void findCollection(){
		IElecTextService elecTextService = (IElecTextService) ServiceProvider.getService(IElecTextService.SERVICE_NAME);
		//实例化PO对象，赋值，执行保存
		ElecTextForm elecTextForm = new ElecTextForm();
		elecTextForm.setTextName("ll");
		elecTextForm.setTextRemark("ha");
		List<ElecText> list = elecTextService.findCollectionByConditionNoPage(elecTextForm);
	}
	
	@Test
	public void test(){
	    //得到类的简写名称
	    System.out.println(ElecTextForm.class.getSimpleName());
	 
	   //得到对象的全路径
	   System.out.println(ElecTextForm.class);
	 
	   //得到对象的类模板示例，也就是Class
	   System.out.println(ElecTextForm.class.getClass());
	 
	   //得到Class类的名称
	   System.out.println(ElecTextForm.class.getClass().getName());
	 } 
	
}
