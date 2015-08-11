package cn.itcast.elec.container;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceProviderCode {

	protected static ApplicationContext ac;
	
	/**
	 * @author 屈卞忠
	 * @param filename 加载beans.xml文件（filename 放置的是beans.xml）
	 */
	public static void load(String filename){
		ac = new ClassPathXmlApplicationContext(filename);
	}
	
}
