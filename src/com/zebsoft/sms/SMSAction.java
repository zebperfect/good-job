package com.zebsoft.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.zzz.util.ConfigInfo;
import com.zebsoft.zzz.util.MyHandlerXml;

/**
 * 短信服务接口
 * @author 张恩备
 * @date 2016-4-13 下午03:48:11
 */
@Controller("smsAction")
@Scope("prototype")
public class SMSAction extends ActionSupport{

	private static final Logger log = LoggerFactory.getLogger(SMSAction.class);	
	private static final String smsUrl = ConfigInfo.getValue("SMSURL");
	private static final String userid = ConfigInfo.getValue("USERID");
	private static final String account = ConfigInfo.getValue("ACCOUNT");
	private static final String password = ConfigInfo.getValue("PASSWORD");
	private static final String content = ConfigInfo.getValue("CONTENT");
	/**
	 * 发送短信
	 * @author 张恩备
	 * @date 2016-4-13 下午03:49:00
	 */
	public String sendSms(){
		this.success =false;
		try {
			String sRand = "";
			for (int i = 0; i < 6; i++) {
				String rand = String.valueOf(new Random().nextInt(10));
				sRand += rand;
			}
			log.info("-------send SMS rand = "+sRand+"-----");
			ActionContext.getContext().getSession().put("smscode", sRand.trim());
			String response = SmsClientSend.sendSms(smsUrl, userid, account, password, regPhone, content+sRand);
			log.info("return response content: " + response);
			// 解析xml
			// 创建一个解析XML的工厂对象
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			// 创建一个解析XML的对象
			SAXParser parser = parserFactory.newSAXParser();
			// 创建一个解析助手类
			MyHandlerXml myhandler = new MyHandlerXml("returnsms");
			parser.parse(new java.io.ByteArrayInputStream(response.getBytes("utf-8")), myhandler);
			log.info(myhandler.getList().toString());
			ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) myhandler
					.getList();
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, String> temp = (HashMap<String, String>) list
						.get(i);
				Iterator<String> iterator = temp.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next().toString();
					String value = temp.get(key);
					System.out.print(key + " " + value + "--");
				}
			}
			this.success = true;
		} catch (Exception e) {
			log.error("sms error : "+e.getMessage(),e);
			this.success=true;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询余额
	 * @author 张恩备
	 * @date 2016-4-13 下午03:49:00
	 */
	public String smsOverage(){
		this.success =false;
		try {
			log.info("-------overage--------");
			String response = SmsClientOverage.queryOverage(smsUrl, userid, account, password);
			log.info("return response content: " + response);
			// 解析xml
			// 创建一个解析XML的工厂对象
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			// 创建一个解析XML的对象
			SAXParser parser = parserFactory.newSAXParser();
			// 创建一个解析助手类
			MyHandlerXml myhandler = new MyHandlerXml("returnsms");
			parser.parse(new java.io.ByteArrayInputStream(response.getBytes("utf-8")), myhandler);
			log.info(myhandler.getList().toString());
			ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) myhandler
			.getList();
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, String> temp = (HashMap<String, String>) list
				.get(i);
				Iterator<String> iterator = temp.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next().toString();
					String value = temp.get(key);
					System.out.print(key + " " + value + "--");
				}
			}
			this.success = true;
		} catch (Exception e) {
			log.error("sms error : "+e.getMessage(),e);
		}
		return SUCCESS;
	}
	private String regPhone;
	private boolean success;

	public String getRegPhone() {
		return regPhone;
	}

	public void setRegPhone(String regPhone) {
		this.regPhone = regPhone;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
