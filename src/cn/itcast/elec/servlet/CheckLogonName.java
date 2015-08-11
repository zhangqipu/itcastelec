package cn.itcast.elec.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.service.IElecUserService;

public class CheckLogonName extends HttpServlet {

IElecUserService elecUserService = (IElecUserService) ServiceProvider.getService(IElecUserService.SERVICE_NAME);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html；charset=utf-8");
		PrintWriter out = response.getWriter();
		String logonName = request.getParameter("logonName");
		/**
		 * checkflag ：判断当前登录名在数据库中是否有值
		 * checkflag ：如果值为1，说明当前登录名在数据库中有重复记录，不能进行保存
		 * checkflag : 如果只为2，说明当前登录名在数据库中灭有重复值，可以进行保存
		 */
		String checkflag = elecUserService.checkLogonName(logonName);
		out.println(checkflag);
		out.flush();
		out.close();

	}
}
