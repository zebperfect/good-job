package com.zebsoft.zzz.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 在这里加载初始数据
 * @author 张恩备
 * @date 2016-4-20 下午06:03:02
 */
public class StartupServlet extends HttpServlet {
	public StartupServlet() {
		super();
	}

	public void destroy() {
		System.out.println("销毁StartupServlet");
		super.destroy();
	}

	public void init() throws ServletException {
		System.out.println("<<<<<<<<<<==init load==>>>>>>>>>>");
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		ServletContext application = this.getServletContext();

	}

}
