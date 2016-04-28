package com.zebsoft.zzz.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zebsoft.right.domain.Users;


/**
 * @author Zeb
 * @version 2014年5月9日19:49:56
 * 类说明
 */
public class AuthorityInterceptor extends AbstractInterceptor {
	  private static final long serialVersionUID = 1358600090729208361L;        
      //拦截Action处理的拦截方法        
      public String intercept(ActionInvocation invocation) throws Exception {        
              // 取得请求相关的ActionContext实例   
              ActionContext ctx=invocation.getInvocationContext();        
              Map session=ctx.getSession();        
              //取出名为user的session属性        
              Users user=(Users)session.get("user");        
              //如果没有登陆，或者登陆所有的用户名不是aumy，都返回重新登陆        
              if(null != user ){    
            	  	  //用户不空，继续走
                      return invocation.invoke();        
              }        
              //没有登陆，将服务器提示设置成一个HttpServletRequest属性        
              ctx.put("tip","您还没有登录，请登陆系统");   
              //用户空，重新登陆
              //如果是ajax请求       拦截器返回的值，不直接回到struts2的result，而是赋给  ajax的返回值
//              outString("<script type=\"text/javascript\">var session='SessionExpired'; function getRootWin(){var win = window;while (win != win.parent){win =win.parent;}return win;} getRootWin().reLogin();</script>");//页面接收 
              outString("UserIsNull");
              return null; // 页面上写一个Login弹出窗口;                        
      } 
      
      public void outString(String str) {  
          HttpServletResponse response = ServletActionContext.getResponse();  
          try {  
              response.setHeader("Pragma", "No-cache");  
              response.setHeader("Cache-Control", "no-cache");  
              response.setDateHeader("Expires", 0);  
              response.setContentType("text/html;charset=utf-8");  
              PrintWriter out = response.getWriter();  
              out.write(str);  
              out.flush();  
              out.close();  
          } catch (IOException e) {  
              e.printStackTrace();  
          }  
      } 
}
