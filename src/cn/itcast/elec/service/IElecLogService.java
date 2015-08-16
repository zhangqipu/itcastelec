package cn.itcast.elec.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.itcast.elec.web.form.ElecLogForm;

public interface IElecLogService {
	public final static String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecLogServiceImpl";

	void saveElecLog(HttpServletRequest request, String details);

	List<ElecLogForm> findElecLogListByCondition(ElecLogForm elecLogForm);

	void deleteElecLogByLogIDs(ElecLogForm elecLogForm);

}
