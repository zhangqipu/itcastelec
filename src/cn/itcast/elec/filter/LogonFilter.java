package cn.itcast.elec.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.elec.domain.ElecUser;

public class LogonFilter implements Filter {

	List<String> list = new ArrayList<String>();
	
	//需要定义系统页面中可放行的链接，放到List<String>的集合对象中
	public void init(FilterConfig arg0) throws ServletException {
		
		list.add("/index.jsp");
		list.add("/image.jsp");
		list.add("/system/elecMenuAction_home.do");
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		/**1、获取页面中的访问的路径链接
		 * 	   *与定义的需要放行的链接进行比对
		 * 			*页面中访问的链接与定义需要放行的链接一致，则此页面中的访问路径链接需要放行
		 * 			*反之，不放行，返回到登录页面
		 *2、从session(globle_user)对象中获取当前登录的用户
		 *		*如果获取的登录用户的对象不为空，则需要放行
		 *		*反之，不放行，返回到登录页面
		 */
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//1、获取页面中的访问的路径链接
		String path = request.getServletPath();
		if(list != null && list.contains(path)){
			//如果页面中获取的访问链接于定义的可放行的链接一致，放行
			chain.doFilter(request, response);
			return ;
		}
		//2、从session(globle_user)对象中获取当前登录的用户
		ElecUser elecUser = (ElecUser) request.getSession().getAttribute("globle_user");
		if(elecUser != null){
			//如果从session中获取的用户对象不为空，则放行
			chain.doFilter(request, response);
			return ;
		}
		//如果不满足条件1和2，返回到登录页面
		response.sendRedirect(request.getContextPath()+"/");
		
	}
	
	@Override
	public void destroy() {
		
	}

	

}
