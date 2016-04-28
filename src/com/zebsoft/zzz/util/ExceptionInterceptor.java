package com.zebsoft.zzz.util;

import java.io.IOException;
import java.sql.SQLException;

import org.hibernate.hql.ast.QuerySyntaxException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * @author mu
 * @since 2011年7月17日9:42:15 
 * struts2 拦截器
 */
public class ExceptionInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) {
		boolean printMsg = true;//是否打印错误信息
		boolean redirect = false;//是否跳转页面
		String result = "";
		String actionName = invocation.getInvocationContext().getName();
		try {
			result = invocation.invoke();
		} catch (QuerySyntaxException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (SQLException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (NullPointerException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (IOException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (ClassNotFoundException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (ArithmeticException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (IllegalArgumentException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (ClassCastException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (SecurityException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (NoSuchMethodError ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (InternalError ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (IllegalStateException ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		} catch (Exception ex) {
			if (printMsg) {
				ex.printStackTrace();
				System.out.println("action的名称为:" + actionName + "\n" + "异常的详细信息" + "\n" + ex.toString());
			}
			if (redirect) {
				return "error";
			}
		}
		return result;
	}
	
}
