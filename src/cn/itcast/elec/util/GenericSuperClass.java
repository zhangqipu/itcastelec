package cn.itcast.elec.util;

import java.lang.reflect.ParameterizedType;

/**
 * @Name:
 * @author Administrator
 *  
 */
public class GenericSuperClass {

	/**
	 * @Name:getClass
	 * @Description : 范类转换，转换为对应的对象
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-02
	 * @Parameters: Class TClass 范类
	 * @return:返回对象
	 */
	@SuppressWarnings("rawtypes")
	public static Class getClass(Class tClass){
		//泛型转换
		ParameterizedType pt = (ParameterizedType) tClass.getGenericSuperclass();
		//如果泛型里有多个，这个数组就可以依次来赋值
		Class entity = (Class) pt.getActualTypeArguments()[0];
		return entity;
	}
}
