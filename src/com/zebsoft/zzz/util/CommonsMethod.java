package com.zebsoft.zzz.util;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 类说明：公共方法：时间格式，字符串格式,路径，判空，随机文件名
 * @author Thinker
 * @since 2014年3月12日10:29:10
 * @version 1.0
 * 
 */
public class CommonsMethod extends ActionSupport {

	private static SimpleDateFormat year = new SimpleDateFormat("yyyy");
	private static SimpleDateFormat month = new SimpleDateFormat("MM");
	private static SimpleDateFormat day = new SimpleDateFormat("dd");
	private static SimpleDateFormat HH = new SimpleDateFormat("HH");
	private static SimpleDateFormat mm = new SimpleDateFormat("mm");
	private static SimpleDateFormat SSS = new SimpleDateFormat("SSS");
	private static SimpleDateFormat yMd = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	/**
	 * @return 返回现在时间，精确到秒，不带文字 例如：20120524104858
	 */
	public static String getNowCorrect2Second() {
		return sdf1.format(new Date());
	}
	
	public static Integer getNowYear(){
		String now = getNowCorrect2Second();
		int nowYear = Integer.valueOf(now.substring(0, 4));
		return nowYear;
	}

	/**
	 * @return 返回现在时间，精确到毫秒秒，不带文字 例如：20120524104858205
	 */
	public static String getNowCorrect2Millisecond() {
		return sdf2.format(new Date());
	}

	/**
	 * @return 返回现在时间，精确到秒，带文字 例如：2012年05月24日10时48分58秒
	 */
	public static String getNowCorrect2SecondWithWord() {
		String now = getNowCorrect2Second();
		return now.substring(0, 4) + "年" + now.substring(4, 6) + "月" + now.substring(6, 8) + "日" + now.substring(8, 10) + "时" + now.substring(10, 12) + "分" + now.substring(12, 14) + "秒";
	}

	/**
	 * @return 返回现在时间，精确到毫秒秒，带文字 例如：2012年05月24日10时48分58秒205毫秒
	 */
	public static String getNowCorrect2MillisecondWithWord() {
		String now = getNowCorrect2Millisecond();
		return now.substring(0, 4) + "年" + now.substring(4, 6) + "月" + now.substring(6, 8) + "日" + now.substring(8, 10) + "时" + now.substring(10, 12) + "分" + now.substring(12, 14) + "秒"
				+ now.substring(14, 17) + "毫秒";
	}
	/**
	 * @return 返回现在时间，精确到秒，带文字 例如：2012年05月24日10时48分58秒
	 */
	public static String changeMillisecondWithWord(String nowData) {
		return nowData.substring(0, 4) + "年" + nowData.substring(4, 6) + "月" + nowData.substring(6, 8) + "日" + nowData.substring(8, 10) + "时" + nowData.substring(10, 12) + "分" + nowData.substring(12, 14) + "秒";
	}

	/**
	 * @return 返回现在日期，精确到天 例如：20120524
	 */
	public static String getToday() {
		return yMd.format(new Date());
	}
	
	/**
	 * @return 返回上个月，精确到月 例如：201605
	 */
	public static String getLastMonth() {
			Calendar c = Calendar.getInstance();
		  c.add(Calendar.MONTH, -1);
		  SimpleDateFormat format =  new SimpleDateFormat("yyyyMM");
		  String time = format.format(c.getTime());
		return time;
	}

	/**
	 * @return 返回当前月，201605
	 */
	public static String getNowMonth() {
		String today = getToday();
		return today.substring(0, 6) ;
	}
	
	/**
	 * @return 返回现在日期，精确到天,带文字 例如：2012年05月24日
	 */
	public static String getTodayWithWord() {
		String today = getToday();
		return today.substring(0, 4) + "年" + today.substring(4, 6) + "月" + today.substring(6, 8) + "日";
	}

	/**
	 * @return 返回现在日期，精确到天,带文字 例如：2012-05-24
	 */
	public static String getTodayDBWithWord() {
		String today = getToday();
		return today.substring(0, 4) + "-" + today.substring(4, 6) + "-" + today.substring(6, 8);
	}

	public static void main(String[] args) {
		// System.out.println(getToday());
		// System.out.println(getTodayWithWord());
		// System.out.println(getNowCorrect2Millisecond());
		// System.out.println(getNowCorrect2MillisecondWithWord());
		// System.out.println(getNowCorrect2Second());
		// System.out.println(getNowCorrect2SecondWithWord());
	}

	/**
	 * 向前台强制返回msg信息格式
	 * @author zeb
	 * @since 2014年3月12日10:31:36
	 * @return
	 */
	public static String returnSuccess() {
		HttpServletResponse response = ServletActionContext.getResponse();
		String msg = "{success:true}";
		try {
			response.getWriter().print(msg);
			response.setContentType("text/html");
		} catch (IOException e) {

		}
		return NONE;
	}

	/**
	 * 强制向前台返回str字符串
	 * @author zeb
	 * @param str
	 * @return
	 */
	public static String returnMsg(String str) {
		HttpServletResponse response = ServletActionContext.getResponse();
		String msg = "{success:true," + str + "}";
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().print(msg);
		} catch (IOException e) {

		}
		return NONE;
	}

	/**
	 * 得到上传文件的路径（如:DOP_CMS/...）
	 * @param rootPath
	 * @param fileName
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public static String getHttpPath(String rootPath, String fileName) throws Exception {
		int index = rootPath.lastIndexOf("\\");
		return rootPath.substring(index) + "/UploadFiles/" + fileName;
	}

	/**
	 * 将数据库中的路径拼成(http://ip:8080/DOP_CMS/....)传给前台显示
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public static String getRealPath(String path) throws Exception {
		return "http://" + getIp() + ":" + getPort() + path;
	}

	/**
	 * 将路径拆分（如:DOP_CMS/...）传到后台保存
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String getVirtualPath(String path) throws Exception {
		String str = "http://" + getIp() + ":" + getPort();
		String virtualPath = path.substring(str.length(), path.length());
		return virtualPath;
	}

	/**
	 * 得到服务端的IP地址
	 * @author Thinker
	 * @return
	 * @throws Exception
	 */
	public static String getIp() throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();
		return ip;
	}


	/**
	 * 得到运行程序的端口号（如:8080）
	 * @return
	 * @throws Exception
	 */
	public static String getPort() throws Exception {
		int port = ServletActionContext.getRequest().getLocalPort();
		return String.valueOf(port);
	}

	/**
	 * 得到工程的物理路径(例如:D:/Workspaces/DOP_CMS/WebRoot/)
	 * @return
	 * @throws Exception
	 */
	public static String getProjectPath() {
		String pathString = Thread.currentThread().getContextClassLoader().getResource("").toString();
		pathString = pathString.replace("file:/", "");
		pathString = pathString.replace("WEB-INF/classes/", "");
		pathString = pathString.replace("%20", " ");
		return pathString;
	}

	/**
	 * 得到工程的物理路径(例如:D:/Workspaces/DOP_CMS/WebRoot/)
	 * @return
	 * @throws Exception
	 */
	public static String getWebRootPath() {
		String rootPath = ServletActionContext.getServletContext().getRealPath("");
		return rootPath;
	}

	/**
	 * 根据时间产生随即的一个文件名称
	 * @return
	 */
	public static String generateFileByDate() {
		String currentTime = sdf2.format(new Date());
		String randomNumber = String.valueOf((int) (Math.random() * 1000));
		String fileName = currentTime + randomNumber;
		return fileName;
	}

	/**
	 * 判断前台传来的查询对象是否为null
	 * @param o
	 * @return
	 */
	public static boolean have(Object o) {
		if (o == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断前台传来的查询条件是否为null或者是否为空格
	 * @param s
	 * @return
	 */
	public static boolean have(String s) {
		if (s == null) {
			return false;
		} else if (s.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}


	/**
	 * 去除重复数据
	 * @since 2014年3月12日10:36:47
	 * @List 
	 */
	public static List removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	/**
	 * 保证长度为3位，不够前面加0
	 * @param s
	 * @return
	 */
	public static String Massaging(String s){
		if(s.length() == 1){
			s = "00"+s;
		} else if(s.length() == 2){
			s = "0"+s;
		} else if(s.length() > 3){
			s = "00a";
		}
		return s;
	}
}
