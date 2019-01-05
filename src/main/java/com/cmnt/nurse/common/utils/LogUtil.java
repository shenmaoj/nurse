package com.cmnt.nurse.common.utils;

import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: LogUtil
 * @Description: TODO 日志记录通用处理类
 * @author 杜贵科
 * @email duguike@mininglamp.com
 * @date 2016年4月1日 上午11:07:35
 * 
 */
public class LogUtil {
	/** 默认日志后退级别 */
	public static final Integer DEFAULT_LOG_STACKTRACE_LEVEL = 3;

	/**
	 * @Title: debug
	 * @Description: TODO 记录调试级别日志信息
	 * @param @param
	 *            msg 调试消息
	 * @return void 无返回值
	 */
	public static void debug(String msg) {
		debug(msg, null);
	}

	/**
	 * @Title: info
	 * @Description: TODO 记录信息级别日志信息
	 * @param @param
	 *            msg 信息消息
	 * @return void 无返回值
	 */
	public static void info(String msg) {
		Integer level = null;
		info(msg, level);

	}

	/**
	 * @Title: info
	 * @Description: TODO 记录信息级别日志信息，并带有异常输出
	 * @param @param
	 *            msg 信息消息
	 * @param @param
	 *            e 抛出异常
	 * @return void 无返回值
	 */
	public static void info(String msg, Exception e) {
		info(msg, e, null);
	}

	/**
	 * @Title: error
	 * @Description: TODO 记录错别级别日志信息，并带有异常输出
	 * @param @param
	 *            msg 错别消息
	 * @param @param
	 *            e 抛出异常
	 * @return void 无返回值
	 */
	public static void error(String msg, Exception e) {
		error(msg, e, null);
	}

	/**
	 * @Title: error
	 * @Description: TODO 记录错别级别日志信息
	 * @param @param
	 *            msg 错别消息
	 * @return void 无返回值
	 */
	public static void error(String msg) {
		Integer level = null;
		error(msg, level);
	}

	/**
	 * @Title: debug
	 * @Description: TODO 记录调试级别日志信息
	 * @param：msg 调试消息
	 * @param: level 调用堆栈后退级别
	 * @return void 无返回值
	 */
	public static void debug(String msg, Integer level) {
		StackTraceElement stack = getCallStackTraceElement(level);
		LoggerFactory.getLogger(stack.getClassName())
				.debug("方法【" + stack.getMethodName() + "】:" + stack.getLineNumber() + "行; " + msg);
	}

	/**
	 * @Title: info
	 * @Description: TODO 记录信息级别日志信息
	 * @param: msg 信息消息
	 * @param: level 调用堆栈后退级别
	 * @return void 无返回值
	 */
	public static void info(String msg, Integer level) {
		StackTraceElement stack = getCallStackTraceElement(level);
		LoggerFactory.getLogger(stack.getClassName()).info("方法【" + stack.getMethodName() + "】:" + stack.getLineNumber() + "行; " + msg);

	}

	/**
	 * @Title: info
	 * @Description: TODO 记录信息级别日志信息，并带有异常输出
	 * @param msg 信息消息
	 * @param  e 抛出异常
	 * @param: level 调用堆栈后退级别
	 * @return void 无返回值
	 */
	public static void info(String msg, Exception e, Integer level) {
		StackTraceElement stack = getCallStackTraceElement(level);
		StringWriter sw = new StringWriter();
		PrintWriter s = new PrintWriter(sw);
		e.printStackTrace(s);
		LoggerFactory.getLogger(stack.getClassName()).info("方法【" + stack.getMethodName() + "】:" + stack.getLineNumber() + "行; " + msg);
		LoggerFactory.getLogger(stack.getClassName()).info("方法【" + stack.getMethodName() + "】:" + stack.getLineNumber() + "行; " + sw.toString());
	}

	/**
	 * @Title: error
	 * @Description: TODO 记录错别级别日志信息，并带有异常输出
	 * @param msg 错别消息
	 * @param e 抛出异常
	 * @param: level 调用堆栈后退级别
	 * @return void 无返回值
	 */
	public static void error(String msg, Exception e, Integer level) {
		StackTraceElement stack = getCallStackTraceElement(level);
		StringWriter sw = new StringWriter();
		PrintWriter s = new PrintWriter(sw);
		e.printStackTrace(s);
		LoggerFactory.getLogger(stack.getClassName()).error("方法【" + stack.getMethodName() + "】:" + stack.getLineNumber() + "行; " + msg);
		LoggerFactory.getLogger(stack.getClassName()).error("方法【" + stack.getMethodName() + "】:" + stack.getLineNumber() + "行; " + sw.toString());
	}

	/**
	 * @Title: error
	 * @Description: TODO 记录错别级别日志信息
	 * @param msg 错别消息
	 * @param: level 调用堆栈后退级别
	 * @return void 无返回值
	 */
	public static void error(String msg, Integer level) {
		StackTraceElement stack = getCallStackTraceElement(level);
		LoggerFactory.getLogger(stack.getClassName()).error("方法【" + stack.getMethodName() + "】:" + stack.getLineNumber() + "行; " + msg);
	}

	/** 
	 * @auth: duguike
	 * @Title: getCallStackTraceElement <br/>
	 * @Description: TODO 获取指定级别调用堆栈<br/> 
	 * @param level 从当前调用开始倒退的调用级别
	 * @return StackTraceElement 
	 */
	private static StackTraceElement getCallStackTraceElement(Integer level) {
		StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
		//修复日志级别错乱问题
		int back_level = -1;
		boolean ischeck = false;
		for (StackTraceElement stackTraceElement : stacks) {
			back_level++;
			if (stackTraceElement.getClassName().equals(LogUtil.class.getName())) {
				ischeck = true; 
			} else {
				if (ischeck) {
					break;
				}
			}
		}
		int _level = level != null ? level : DEFAULT_LOG_STACKTRACE_LEVEL;
		_level=back_level;
		StackTraceElement stack = null;
		if (stacks.length > _level)
			stack = stacks[_level];
		else
			stack = stacks[stacks.length - 1];
		return stack;
	}
}
