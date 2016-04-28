package com.zebsoft.zzz.util;

import java.util.Map;


import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zebsoft.right.domain.Users;

@Controller("logerHelper")
public class LogHelper {

	public static String ipInstence() {
		return ServletActionContext.getRequest().getRemoteAddr().toString();
	}
	
	public static Users initUser(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		Users user = (Users) session.get("user");
		return user;
	}

	public static String userLog(String caozuoguocheng) {
		return "#操作过程为：" + caozuoguocheng + " #用户名：#" + initUser().getUsername()+ "# 机器ip#" + ipInstence() + "# 时间 " + CommonsMethod.getNowCorrect2SecondWithWord();
	}
	
	public static String fSendEmail(String name,String email){
		return "name：<"+name+">，email：<"+email+"> send success"+",send time：<"+CommonsMethod.getNowCorrect2SecondWithWord()+">";
	}

}