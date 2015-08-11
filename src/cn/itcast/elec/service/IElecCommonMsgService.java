package cn.itcast.elec.service;

import java.util.List;

import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.web.form.ElecCommonMsgForm;
import cn.itcast.elec.web.form.ElecTextForm;

public interface IElecCommonMsgService {
	public final static String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecCommonMsgServiceImpl";

	List<ElecCommonMsgForm> findElecCommonMsgList();

	void saveElecCommonMsg(ElecCommonMsgForm elecCommonMsgForm);

	List<ElecCommonMsgForm> findElecCommonMsgListByCurrentDate();
	
	
}
