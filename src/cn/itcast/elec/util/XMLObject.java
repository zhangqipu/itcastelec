package cn.itcast.elec.util;
/**
 * 存放Function.xml 文件中
 * 获取的权限 权限code 权限名称 父级权限code 父级权限名称
 * @author Administrator
 *
 */
public class XMLObject {

	private String code;		//权限code
	private String name;		//权限名称
	private String parentCode;	//父级权限code
	private String parentName;	//父级权限名称
	/**
	 * 判断页面中的角色编辑时，权限的复选框是否被选中的标识
	 * 如果 flag = 0；表示该角色不具有权限，则页面中权限复选框不被选中
	 * 如果flag = 1，表示该角色具有此权限，泽雅绵中权限复选框被选中
	 */
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
