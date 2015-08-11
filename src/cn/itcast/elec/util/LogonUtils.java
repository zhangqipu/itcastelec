package cn.itcast.elec.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class LogonUtils {

	/**
	 * @Name: checkNumber
	 * @Description :   首页登录时，添加验证码的功能的：
	 * 							比较验证码的值与所输入的值是否相等
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-11
	 * @Parameters: HttpServletRequest request  对象
	 * @return: boolean     true：验证成功
	 * 						false：验证失败
	 */
	public static boolean checkNumber(HttpServletRequest request) {
		// 从session中获取验证码的数值
		HttpSession session = request.getSession(false);
		if(session == null){
			return false;
		}
		String check_number_key = (String)session.getAttribute("CHECK_NUMBER_KEY");
		if(StringUtils.isBlank(check_number_key)){
			return false;
		}
		//从登陆页面获取验证码的值
		String checkNumber = request.getParameter("checkNumber");
		if(StringUtils.isBlank(checkNumber)){
			return false;
		}
		
		return check_number_key.equalsIgnoreCase(checkNumber);  
	}

	/**
	 * @Name: remeberMeByCookie
	 * @Description :   首页登录记住我的功能
	 * @author 屈卞忠
	 * @version :V1.0.0(版本号)
	 * @Create Date ：2015-08-11
	 * @Parameters: HttpServletRequest   request     对象
	 * 				HttpServletResponse  response    对象
	 * @return: null
	 * @throws UnsupportedEncodingException 
	 */
	public static void remeberMeByCookie(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// 获取页面中的登录名和密码
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		//处理Cookie中存在的中文字符
		String codeName = URLEncoder.encode(name, "utf-8");
		String codePassword = URLEncoder.encode(password, "utf-8");
		//创建两个Cookie，分别存放登录名和密码
		Cookie nameCookie = new Cookie("name", codeName);
		Cookie passwordCookie = new Cookie("password", codePassword);
		//设置Cookie的有效路径，有效路径定义为项目的根本路径
		nameCookie.setPath(request.getContextPath()+"/");
		passwordCookie.setPath(request.getContextPath()+"/");
		/**
		 * 从页面中获取记住我的复选框的值，
		 *	     如果有值，设置Cookie的有效时长
		 *	     如果没有值，清空Cookie的有效时长
		*/
		String remeberMe = request.getParameter("remeberMe");
		//设置Cookie的有效时长
		if(remeberMe != null && remeberMe.equals("yes")){
			nameCookie.setMaxAge(7*24*60*60);
			passwordCookie.setMaxAge(7*24*60*60);
		}
		//清空Cookie的有效时长
		else{
			nameCookie.setMaxAge(0);
			passwordCookie.setMaxAge(0);
		}
		//将2个Cookie对象存放到response对象中
		response.addCookie(nameCookie);
		response.addCookie(passwordCookie);
	}

}
