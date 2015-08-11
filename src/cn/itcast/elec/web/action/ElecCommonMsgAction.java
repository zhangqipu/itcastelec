package cn.itcast.elec.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.service.IElecCommonMsgService;
import cn.itcast.elec.web.form.ElecCommonMsgForm;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ElecCommonMsgAction extends BaseAction implements ModelDriven<ElecCommonMsgForm> {

	private ElecCommonMsgForm elecCommonMsgForm = new ElecCommonMsgForm();
	private IElecCommonMsgService elecCommonMsgService = (IElecCommonMsgService) ServiceProvider.getService(IElecCommonMsgService.SERVICE_NAME);

	@Override
	public ElecCommonMsgForm getModel() {
		return elecCommonMsgForm;
	}

	/**
	 * @Name:home
	 * @Description : 查询待办事宜列表
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: 无
	 * @return:String home 跳转到actingIndex.jsp
	 */
	public String home(){
		List<ElecCommonMsgForm> list = elecCommonMsgService.findElecCommonMsgList();
		request.setAttribute("commonList", list);
		return "home";
	}

	/**
	 * @Name:save
	 * @Description : 保存待办事宜信息
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-03
	 * @Parameters: 无
	 * @return:String save 重定向到actingIndex.jsp
	 */
	public String save(){
		elecCommonMsgService.saveElecCommonMsg(elecCommonMsgForm);
		return "save";
	}

	
}
