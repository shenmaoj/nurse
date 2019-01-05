package com.cmnt.nurse.common.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 如果没有注入默认的日期格式，也没有配置<value>WriteDateUseDateFormat</value>,
 * 也没有属性注解@JSONField(format="yyyy-MM-dd hh:mm:ss") 则会转换输出时间戳 如果只配置
 * <value>WriteDateUseDateFormat</value>，则会转换输出yyyy-MM-dd hh:mm:ss 配置
 * <value>WriteDateUseDateFormat</value>, 属性注解@JSONField(format="yyyy-MM-dd
 * hh:mm:ss") 则会转换输出为属性注解的格式 如果注入了默认的日期格式，属性注解@JSONField(format="yyyy-MM-dd
 * hh:mm:ss") 则会转换输出为属性注解的格式 如果注入了默认的日期格式，则会转换输出为默认的日期格式 如果三者都配置则会转换成属性注解的格式
 */
public class SpringFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
	
	public static SerializeConfig mapping = new SerializeConfig();
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		OutputStream out = outputMessage.getBody();
		SerializeWriter serializeWriter = new SerializeWriter(super.getFeatures());
		JSONSerializer serializer = new JSONSerializer(serializeWriter, mapping);
		serializer.write(obj);
		String text = serializer.toString();
		out.write(text.getBytes(getCharset()));
	}
	 
}