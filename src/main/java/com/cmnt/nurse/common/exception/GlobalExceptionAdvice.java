package com.cmnt.nurse.common.exception;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex) {
        if(ex.getCause() instanceof  BusinessException){
            BusinessException businessException = (BusinessException) ex.getCause();
            return BusinessException.toMap(businessException.getCode());
        }
        logger.error(ex.getMessage());
        return BusinessException.toMap("90000");
    }

    @ResponseBody
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public Map errorHandler1(IncorrectCredentialsException ex) {
        logger.error(ex.getMessage());
        return BusinessException.toMap("90003");
    }

    /**
     * 拦截捕捉自定义异常 BusinessException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Map myErrorHandler(BusinessException ex) {
        return BusinessException.toMap(ex.getCode());
    }
}
