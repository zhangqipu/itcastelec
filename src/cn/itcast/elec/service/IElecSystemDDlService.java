package cn.itcast.elec.service;

import java.util.List;

import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.web.form.ElecCommonMsgForm;
import cn.itcast.elec.web.form.ElecSystemDDlForm;
import cn.itcast.elec.web.form.ElecTextForm;

public interface IElecSystemDDlService {
	public final static String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecSystemDDlImpl";

	List<ElecSystemDDlForm> findKeyWord();

	List<ElecSystemDDlForm> findElecSystemDDLListByKeyWord(String keyWord);

	void saveElecSystemDDl(ElecSystemDDlForm elecSystemDDlForm);

}
