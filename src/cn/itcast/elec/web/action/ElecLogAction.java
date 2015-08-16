package cn.itcast.elec.web.action;

import java.util.List;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.service.IElecLogService;
import cn.itcast.elec.web.form.ElecLogForm;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ElecLogAction extends BaseAction implements ModelDriven<ElecLogForm>{

	private ElecLogForm elecLogForm = new ElecLogForm();
	
	private IElecLogService elecLogService = (IElecLogService) ServiceProvider.getService(IElecLogService.SERVICE_NAME);
	
	@Override
	public ElecLogForm getModel() {
		return elecLogForm;
	}

	/**
	 * @Name: home
	 * @Description : 查询日志列表信息 
	 * @author: 屈卞忠
	 * @version : V1.0.0 版本
	 * @Create Date ： 2015-08-11
	 * @Parameters: null
	 * @return: String home 跳转到logIndex.jsp
	 */
	public String home(){
		List<ElecLogForm> list = elecLogService.findElecLogListByCondition(elecLogForm);
		request.setAttribute("logList", list);
		return "home";
	}
	
	/**
	 * @Name: delete
	 * @Description : 删除查询得到的日志列表信息 
	 * @author: 屈卞忠
	 * @version : V1.0.0 版本
	 * @Create Date ： 2015-08-16
	 * @Parameters: null
	 * @return: String delete 重定向到logIndex.jsp
	 */
	public String delete(){
		//第三种方式
		String logid [] = request.getParameterValues("logID");
		elecLogService.deleteElecLogByLogIDs(elecLogForm);
		return "delete";
	}
}
