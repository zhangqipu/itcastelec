package cn.itcast.elec.container;

import org.apache.commons.lang.StringUtils;

public class ServiceProvider {
	private static ServiceProviderCode spc;
	
	//加载配置文件beans.xml文件
	static{
		spc = new ServiceProviderCode();
		spc.load("beans.xml");
	}
	
	/**
	 * @param serviceName 获取当前服务的名称
	 * @return 返回当前服务
	 */
	public static Object getService(String serviceName){
		
		if(StringUtils.isBlank(serviceName)){
			throw new RuntimeException("当前服务器名称不存在");
		}
		Object object = null;
		if(spc.ac.containsBean(serviceName)){
			object = spc.ac.getBean(serviceName);
		}
		if(object == null){
			throw new RuntimeException("当期服务名称【 "+serviceName+"】 下的服务节点不存在");
		}
		
		return object;
	}
}
