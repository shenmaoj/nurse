package com.cmnt.nurse.common.utils;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: BoToMapUtils
 * @Description: TODO Bo转 Map工具类
 * @author 杜贵科
 * @email duguike@qq.com
 * @date 2016年5月21日 下午5:30:04
 * 
 */
public class BoToMapUtils {

	/**
	 * @auth: duguike
	 * @Title: toMap <br/>
	 * @Description: TODO BO对象转换为Map<br/>
	 * @param parameterObject
	 * @return
	 */
	public static Map<String, Object> toMap(Object parameterObject) {
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		MetaObject metaObject = SystemMetaObject.forObject(parameterObject);
		for (String name : metaObject.getGetterNames()) {
			paramMap.put(name, metaObject.getValue(name));
		}
		return paramMap;
	}

}
