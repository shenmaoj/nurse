package com.cmnt.nurse.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * @author 陈伟
 * @version ST_V1.0
 * @(#) MSGResources
 * <p>
 * @版权： 四川中疗网络科技有限公司
 * <p>
 * @描述： 国际化获取资源文件信息
 * <p>
 * @createDate 2016年4月28日
 * @see
 */
public class MSGResources {

    private static final Logger logger = LoggerFactory.getLogger(MSGResources.class);
    /**
     * 中文环境
     */
    private static ResourceBundle res = null;

    /**
     * 初始化
     *
     * @description
     * @author 陈伟
     * @createDate 2016年4月28日
     * @version ST_V1.0
     */
    private static void initResources() {

        if (res == null) {
            Locale cn = new Locale("zh", "CN");
            res = ResourceBundle.getBundle("messages.messages", cn);
        }

    }

    /**
     * 使用键值获取消息
     *
     * @param key
     * @return
     * @description
     * @author 陈伟
     * @createDate 2016年4月28日
     * @version ST_V1.0
     */
    public static String getMessageByKey(String key) {
        initResources();
        String msg = "";
        try {
            msg = res.getString(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;

    }
}
