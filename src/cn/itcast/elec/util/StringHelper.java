package cn.itcast.elec.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**  
 * @Name: stringConvertDate
 * @Description: 将字符串形式的类型转换成日期类型
 * @Author: 屈卞忠（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-07-30 （创建日期）
 * @Parameters: String date 字符串类型的日期形式
 * @Return: Date 日期类型
 */
public class StringHelper {
	public static Date stringConvertDate(String textDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(textDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
