package com.cmnt.nurse.common.utils;

import org.springframework.util.Assert;

import javax.servlet.ServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WebUtils {
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0].trim().replaceAll(" ", ""));
				}
			}
		}
		return params;
	}

	/**
	 * 处理post请求参数前后空格
	 * @param map
	 * @return
	 */
	public static Map<String, Object> postParameters(Map<String,Object> map) {
		Assert.notNull(map, "Map must not be null");
		Set<Map.Entry<String, Object>> entry = map.entrySet();
		for (Map.Entry<String, Object> stringObjectEntry : entry) {
			if(null != stringObjectEntry.getValue() && StringUtils.isNotBlank(stringObjectEntry.getValue().toString())){
				stringObjectEntry.setValue(stringObjectEntry.getValue().toString().trim());
			}
		}
		return map;
	}
}
