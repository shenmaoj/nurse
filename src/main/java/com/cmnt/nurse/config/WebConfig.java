package com.cmnt.nurse.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cmnt.nurse.common.fastjson.SpringFastJsonHttpMessageConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @auther shenmj
 * @create 2019/1/1
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectMapper objectMapper = builder.build();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);// 忽略 transient 修饰的属性
        SpringFastJsonHttpMessageConverter springFastJsonHttpMessageConverter = new SpringFastJsonHttpMessageConverter();
        SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.SkipTransientField,
        SerializerFeature.QuoteFieldNames,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.DisableCircularReferenceDetect};
        springFastJsonHttpMessageConverter.setFeatures(features);
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        converters.add(springFastJsonHttpMessageConverter);
        super.configureMessageConverters(converters);
    }
}
