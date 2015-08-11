package cn.itcast.elec.web.form;

import java.io.Serializable;
import java.util.Date;
/**
 * VO 对象，对应页面表单的属性值
 * PO 对象与VO对象的关系：
 * 	不同点：
 *		PO对象中的属性关联数据库的字段
 *		VO对象中的属性可以随意增加、修改、删除，对应的是页面的表单属性
 */
@SuppressWarnings("serial")
public class ElecCommonMsgForm implements Serializable{
	private String comID;			//主键ID
	private String stationRun;		//站点运行情况
	private String devRun;			//设备运行情况
	private String createDate;		//创建日期
	public String getComID() {
		return comID;
	}
	public void setComID(String comID) {
		this.comID = comID;
	}
	public String getStationRun() {
		return stationRun;
	}
	public void setStationRun(String stationRun) {
		this.stationRun = stationRun;
	}
	public String getDevRun() {
		return devRun;
	}
	public void setDevRun(String devRun) {
		this.devRun = devRun;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
