package cn.itcast.elec.web.form;

import java.io.Serializable;
import java.util.Date;

/**
 * VO持久层对象,对应页面上的日志操作。
 * @author 屈卞忠
 *
 */
public class ElecLogForm implements Serializable {

	private String LogID;		//主键ID
	private String IpAddress;	//IP地址
	private String OpeName;		//操作人
	private String OpeTime;	//操作时间
	private String Details;		//操作明细
	
	public String getLogID() {
		return LogID;
	}
	public void setLogID(String logID) {
		LogID = logID;
	}
	public String getIpAddress() {
		return IpAddress;
	}
	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}
	public String getOpeName() {
		return OpeName;
	}
	public void setOpeName(String opeName) {
		OpeName = opeName;
	}
	public String getOpeTime() {
		return OpeTime;
	}
	public void setOpeTime(String opeTime) {
		OpeTime = opeTime;
	}
	public String getDetails() {
		return Details;
	}
	public void setDetails(String details) {
		Details = details;
	}
	
}
