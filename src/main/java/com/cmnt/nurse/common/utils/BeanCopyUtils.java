package com.cmnt.nurse.common.utils;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;


/**
 * @ClassName: BeanCopyUtils
 * @Description: TODO Bean属性复制工具类
 * @author 杜贵科
 * @email duguike@qq.com
 * @date 2016年5月26日 下午2:44:20
 * 
 */
public class BeanCopyUtils {

	/**
	 * @auth: duguike
	 * @Title: copyProperties <br/>
	 * @Description: TODO 参数拷贝，支持Map拷贝<br/>
	 * @param source
	 * @param target
	 */
	@SuppressWarnings("unchecked")
	public static void copyProperties(Object source, Object target) {
		if (source == null || target == null) {
			LogUtil.debug(" source or target is null, you copy maoxian");
			return;
		}
		MetaObject sourcemetaObject = SystemMetaObject.forObject(source);
		MetaObject targetmetaObject = SystemMetaObject.forObject(target);
		if (target instanceof Map) {
			((Map<String, Object>) target).putAll(BoToMapUtils.toMap(source));
			return;
		}
		for (String name : sourcemetaObject.getGetterNames()) {
			String propName = targetmetaObject.findProperty(name, false);
			if (StringUtils.isBlank(propName)) {
				continue;
			}
			Class<?> sourceclazz = sourcemetaObject.getGetterType(name);
			Class<?> targetclazz = targetmetaObject.getGetterType(propName);
			if (sourceclazz == targetclazz) {
				targetmetaObject.setValue(propName, sourcemetaObject.getValue(name));
			} else {
				if ((sourceclazz == Date.class || sourceclazz == Timestamp.class)
						&& targetclazz == java.util.Date.class) {
					Long date = null;
					if (sourceclazz == Date.class) {
						Date tempDate = (Date) sourcemetaObject.getValue(name);
						if (tempDate != null)
							date = tempDate.getTime();
					} else if (sourceclazz == Timestamp.class) {
						Timestamp tempDate = (Timestamp) sourcemetaObject.getValue(name);
						if (tempDate != null)
							date = tempDate.getTime();
					}
					if (date != null) {
						java.util.Date utilDate = new java.util.Date(date);
						targetmetaObject.setValue(propName, utilDate);
					}
				} else {
					LogUtil.debug("source and target   property'" + name + "' do not match the type  ");
				}
			}
		}
		
	}

	public static Object convertEntity(Map<String, Object> map, Object object) {
		copyProperties(map, object);
		return object;
	}
 
}