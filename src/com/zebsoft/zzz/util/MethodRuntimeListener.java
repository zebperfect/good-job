package com.zebsoft.zzz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MethodRuntimeListener {

	/**
	 * @author ROC
	 * @since 2010年7月22日9:58:18
	 * @description 监测方法的运行时间 在业务方法的开始出调用
	 *              startTime()，结束处调用endTime()，日志记录该方法的实际运行时间，精确至纳秒
	 */

	private long startTime;
	private long endTime;
	private String methodName;
	private Logger logger;

	/**
	 * @param logger
	 *            传递的业务logger 
	 * 构造方法推荐使用此种，只传递一个Logger
	 */
	public MethodRuntimeListener(Logger logger) {
		this.logger = logger;
		this.startTime = this.endTime = 0;

	}

	public MethodRuntimeListener(Logger logger, String methodName) {
		this.logger = logger;
		this.methodName = methodName;
		this.startTime = this.endTime = 0;

	}

	/**
	 * 开始处，需传入方法名（推荐使用此种方式）
	 */
	public void startTime(String methodName) {
		this.methodName = methodName;
		this.startTime = System.nanoTime();
	}

	public void startTime() {
		this.startTime = System.nanoTime();
	}

	public void endTime() {
		this.endTime = System.nanoTime();
		this.countRunTime();
	}

	public void countRunTime() {
		long runTime = this.endTime - this.startTime;
		logger.info("业务方法{}运行时长为{}纳秒", methodName, runTime);
	}

//	public static void main(String[] args) {
//		// Logger logger2 = LoggerFactory.getLogger("healthcardbusiness");
//		// String tempString[] = { "=========", "---------", "@@@@@@@@@" };
//		// String tempString1 = "11111111111111";
//		// String tempString2 = "22222222222222";
//		// String tempString3 = "33333333333333";
//		// logger2.info("数据为：{},{},{}", tempString);
//		// logger2.info("数据为：{},{}", tempString1, tempString2);
//		// methodRuntimeLister.endTime();
//	}
}
