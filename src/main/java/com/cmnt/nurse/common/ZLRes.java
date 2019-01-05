package com.cmnt.nurse.common;


import com.cmnt.nurse.common.exception.BusinessException;

import java.io.Serializable;

/**
 * @author 陈伟
 * @version ST_V1.0
 * @(#) STRes
 * <p>
 * @版权： 空间时间文化传播有限公司
 * <p>
 * @描述： 响应封装
 * <p>
 * @createDate 2016年4月26日
 * @see
 */
public class ZLRes<T> implements Serializable {

    /**   */
    private static final long serialVersionUID = 6532179907636663559L;
    /**
     * 返回码 5个零表示成功
     */
    private String resCode = "00000";
    /**
     * 返回信息
     */
    private String resMsg = "成功";

    /**
     * 数据体
     */
    private T data;

    public ZLRes(T data) {
        setData(data);
    }

    public ZLRes() {

    }

    public ZLRes(BusinessException ex) {
        this.resCode = ex.getCode();
        this.resMsg = ex.getMessage();
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
