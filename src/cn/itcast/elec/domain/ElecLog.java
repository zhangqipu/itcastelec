package cn.itcast.elec.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * PO持久层对象，对应数据库表Elec_Log
 * @author 屈卞忠
 * @create Date 2015-08-11
 */
@SuppressWarnings("serial")
public class ElecLog implements Serializable {
	private String LogID;		//主键ID
	private String IpAddress;	//IP地址
	private String OpeName;		//操作人
	private Date OpeTime;	//操作时间
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
	public Date getOpeTime() {
		return OpeTime;
	}
	public void setOpeTime(Date opeTime) {
		OpeTime = opeTime;
	}
	public String getDetails() {
		return Details;
	}
	public void setDetails(String details) {
		Details = details;
	}
	
}
