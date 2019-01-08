package com.cmnt.nurse.common.utils;

import com.cmnt.nurse.common.exception.BusinessException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @(#) StringUtils.java
 * @版权： 四川中疗网络科技有限公司
 * @描述： 
 * @author leic
 * @version cme_cd_V1.0
 * @createDate 2017年7月21日
 * @see
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * @param data
	 * @return md5字符串
     */
	public static String toMd5(String data) {
		return DigestUtils.md5Hex(data);
	}

	/**
	 * @return UUID去除-
	 */
	public static String createId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 
	 * @param request
	 * @return ip
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String ip = request.getRemoteAddr();

		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		return ip;
	}


	/**
	 * 随机生成6位数验证码
	 * 
	 * @description
	 * @author LIUK
	 * @createDate 2017年2月5日
	 * @return
	 * @version cmec_V1.0
	 *
	 */
	public static String getRandNum() {
		int randomNum = (int) ((Math.random() * 9 + 1) * 100000);
		return String.valueOf(randomNum);
	}

	/**
	 * 
	 * @Description: 对数据字符串进行加1操作
	 * @author leic
	 * @date 2017年5月24日 下午6:52:19
	 * @param val
	 * @return +1字符串
	 */
	public static String addStrValue(String val) {
		if (val != null && !"".equals(val)) {
			int index = 0;
			int vLen = val.length();
			for (int i = 0; i < vLen; i++) {
				char c = val.charAt(i);
				if (c != '0') {
					break;
				}
				index++;
			}
			String nVal = "";
			if (index == vLen) {
				nVal = "1";
			} else {
				String intVal = val.substring(index);
				nVal = String.valueOf(Integer.valueOf(intVal) + 1);
			}
			int beginIndex = nVal.length() - vLen;
			if (beginIndex > 0) {
				return nVal.substring(beginIndex);
			} else {
				int len = vLen - nVal.length();
				for (int i = 0; i < len; i++) {
					nVal = "0" + nVal;
				}
				return nVal;
			}
		}
		return "";
	}
	
	public static String getSubStr(String val, String space, int num) {
		int i = 0;
		String result = val;
		while(i < num) {
		    int lastNum = result.lastIndexOf(space);
		    result = val.substring(0, lastNum);
		    i++;
		}
		return result;
    } 

	/**
	 * 验证是否合法手机号
	 * @param mobile
	 * @return  Boolean
	 */
	public static Boolean validMobile(String mobile, String regex) {
		if (isBlank(mobile)) {
			return false;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobile);
		if (!m.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 格式化page参数
	 * 
	 * @param param
	 * @return param
	 */
	public static Map<String, Object> formatParam(Map<String, Object> param) {
		int page, pageSize;
		try {
			page = Integer.parseInt(param.get("page").toString());
		} catch (Exception e) {
			page = 1;
		}
		try {
			pageSize = Integer.parseInt(param.get("pageSize").toString());
		} catch (Exception e) {
			pageSize = 10;
		}
		param.put("page", page);
		param.put("pageSize", pageSize);
		return param;
	}
	
	/**
	 * 参数检查
	 * @param map 参数集合map
	 * @param errorCode 异常编号：多个","号隔开
	 * @param validParam 校验参数名：多个","号隔开
	 * @return
	 */
	public static Boolean paramCheck(Map<String, Object> map, String errorCode, String validParam){
		String[] errorCodes = errorCode.split(",");
		String[] validParams = validParam.split(",");
		for (int i = 0; i < validParams.length; i++) {
			if(null==map.get(validParams[i]) || (null!=map.get(validParams[i]) && StringUtils.isBlank(map.get(validParams[i]).toString()))){
				if(errorCodes.length==1 || i > errorCodes.length){
					throw BusinessException.creat(errorCodes[0]);
				}else{
					throw BusinessException.creat(errorCodes[i]);
				}
			}
		}
		return true;
	}

}