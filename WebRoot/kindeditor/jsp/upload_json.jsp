<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.concurrent.locks.*"%>
<%@ page import="java.io.*"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper"%>


<%
	HashMap<String, String> extMap = new HashMap<String, String>();
	extMap.put("image", "gif,jpg,jpeg,png,bmp");
	extMap.put("flash", "swf,flv");
	extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
	extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	//Struts2 请求 包装过滤器
	String dirName = request.getParameter("dir");
	if (dirName == null) {
		dirName = "image";
	}
	
	MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) request;
	//获得上传的文件名
	String fileName = wrapper.getFileNames("imgFile")[0];
	//获得未见过滤器
	File file = null;
	try{
		file = wrapper.getFiles("imgFile")[0];
	}catch(Exception e){
		out.println(getError("上传文件太大。\n只允许上传50MB的文件。"));
		return;
	}
	//----------重新构建上传文件名---------
	final Lock lock = new ReentrantLock();
	String newName = null;
	lock.lock();
	try {
		//加锁为防止文件名重复
		newName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
	} finally {
		lock.unlock();
	}
	
	String fileExt = newName.substring(newName.lastIndexOf(".") + 1).toLowerCase();
	if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
		out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
		return;
	}
	
	//------锁结束---------
	//获取文件输出流
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	String ymd = sdf.format(new Date());
	String newPath = request.getSession().getServletContext().getRealPath("/") + "/attached/"+dirName+"/" +ymd;
	File saveDirFile = new File(newPath);
	if (!saveDirFile.exists()) {
		saveDirFile.mkdirs();
	}
	FileOutputStream fos = new FileOutputStream(newPath+"/" + newName);
	//设置 KE 中的图片文件地址
	String newFileName = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/attached/"+dirName+"/" +ymd+"/"+ newName;
	byte[] buffer = new byte[1024];
	//获取内存中当前文件输入流
	InputStream in = new FileInputStream(file);
	try {
		int num = 0;
		while ((num = in.read(buffer)) > 0) {
			fos.write(buffer, 0, num);
		}
	} catch (Exception e) {
		e.printStackTrace(System.err);
	} finally {
		in.close();
		fos.close();
	}
	//发送给 KE 

	JSONObject obj = new JSONObject();
	obj.put("error", 0);
	obj.put("url", newFileName);
	out.println(obj.toJSONString());
%>
<%!private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}%>