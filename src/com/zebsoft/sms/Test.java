package com.zebsoft.sms;

public class Test {

	public static String url = "http://121.199.50.122:8888/sms.aspx";
	public static String userid = "1411";
	public static String account = "QDRXDSM";
	public static String password = "12345678";
	public static String checkWord = "【pos注册】感谢您使用注册本系统，注册验证码为：";

	public static void main(String[] args) {

		keyword();
		// overage();
	}

	public static void keyword() {

		String keyword = SmsClientKeyword.queryKeyWord(url, userid, account,
				password, checkWord);
		System.out.println(keyword);
	}

	public static void overage() {

		String overage = SmsClientOverage.queryOverage(url, userid, account,
				password);
		System.out.println(overage);
	}

	public static void test() {
		String send = SmsClientAccessTool.getInstance().doAccessHTTPPost(
				"http://118.145.30.35/sms.aspx", "", "utf-8");
		System.out.println(send);
	}
}
