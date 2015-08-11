package cn.itcast.elec.service;

import java.util.List;

import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.web.form.ElecTextForm;

public interface IElecTextService {
	public final static String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecTextServiceImpl";
	public void saveElecText(ElecText elecText);
	public List<ElecText> findCollectionByConditionNoPage(ElecTextForm elecTextForm);
	
}
