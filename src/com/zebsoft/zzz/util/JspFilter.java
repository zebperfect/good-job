package com.zebsoft.zzz.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zebsoft.right.domain.Menu;
import com.zebsoft.right.domain.Users;

/**
 * 用于权限拦截的过滤器
 * 
 */
public class JspFilter implements Filter {
	private FilterConfig filterConfig;

	public void destroy() {
		this.filterConfig = null;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String url = httpRequest.getServletPath().substring(1).trim();
		HttpSession session = httpRequest.getSession();
		List<Menu> menus = (List<Menu>) session.getAttribute("menu");
		boolean flag = false;
		if (url != null
				&& !url.equals("")
				&& (url.equals("index.jsp")||url.equals("login.jsp")||url.equals("defaultIndex.jsp")||url.equals("exportExcelUrl.jsp")
						||url.contains("pages/"))) {
			System.out.println(url+"============");
			chain.doFilter(request, response);
		} else if (url != null && !url.equals("")) {
			System.out.println(url+"!!!!!!!!!!!!!!!!!!!!!!");
			Users user = (Users) session.getAttribute("user");
			if ((user != null)) {
				// 判断获取的路径不为空且不是访问登录页面
				if (url.indexOf("login.jsp") == -1) {
					for (Menu menu : menus) {
						if (url.equals(menu.getUrl())) {
							System.out.println(url+"@@@@@@@@@@2");
							flag = true;
						}
					}
					if (flag) {
						chain.doFilter(request, response);
					} else {
						RequestDispatcher rd = request.getRequestDispatcher("/noRight.jsp");
						rd.forward(request, response);
					}
				} else {
					chain.doFilter(request, response);
				}
			} else {
//				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
				java.io.PrintWriter out = response.getWriter();  
			    out.println("<html>");  
			    out.println("<script>");  
			    out.println("window.open ('/login.jsp','_top')");  
			    out.println("</script>");  
			    out.println("</html>");
				return;
			}
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
		}
	}
}
