package com.cmnt.nurse.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.net.URLDecoder;

public class HttpPostUtil {

    public static String postRequest(String url, JSONObject jsonParam) throws Exception {

        CloseableHttpClient httpsClient = HttpClientBuilder.create().build();
        HttpPost method = new HttpPost(url);
        String resultContent = null;
        if (null != jsonParam) {
            //解决中文乱码问题
            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);
        }
        HttpResponse result = httpsClient.execute(method);
        url = URLDecoder.decode(url, "UTF-8");
        /**请求发送成功，并得到响应**/
        if (result.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = result.getEntity();
            resultContent = EntityUtils.toString(he, "UTF-8");
        }
        return resultContent;
    }

}