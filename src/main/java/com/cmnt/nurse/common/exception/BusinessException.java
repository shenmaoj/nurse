package com.cmnt.nurse.common.exception;


import com.cmnt.nurse.common.MSGResources;

import java.util.HashMap;
import java.util.Map;

/**
 * @(#) STException
 *      <p>
 * 		@版权： 空间时间文化传播有限公司
 *      <p>
 * 		@描述： 业务异常
 *      <p>
 * @author 陈伟
 * @version ST_V1.0
 * @createDate 2016年4月28日
 * @see
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4494005556313966026L;

	/**
	 * 异常编号
	 */
	private String code;

	/**
	 * 异常构建
	 * 
	 * @description
	 * @author 陈伟
	 * @createDate 2016年4月28日
	 * @param code
	 *            异常编号
	 * @param message
	 *            异常消息
	 * @version ST_V1.0
	 * 
	 */
	public BusinessException(String code, String message) {
		super(message);
		this.code = code;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 使用异常编码创建业务异常
	 * 
	 * @description
	 * @author 陈伟
	 * @createDate 2016年4月28日
	 * @param code
	 *            业务异常编码
	 * @return
	 * @version ST_V1.0
	 * 
	 */
	public static BusinessException creat(String code) {
		String message = MSGResources.getMessageByKey(code);
		return new BusinessException(code, message);

	}

	public static Map<String,Object> toMap(String code){
		BusinessException businessException = creat(code);
		Map<String,Object> map = new HashMap<>();
		map.put("code",code);
		map.put("message",businessException.getMessage());
		return map;
	}
}
