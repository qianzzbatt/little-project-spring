package com.ruyuan.little.project.spring.handler;

import com.ruyuan.little.project.common.dto.CommonResponse;
import com.ruyuan.little.project.spring.expection.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author huangquan
 * @Description 全局异常处理
 * @Date 2022/3/22
 **/

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public CommonResponse handleException(Exception e) {
        logger.error("系统异常错误信息:{}", e);
        return CommonResponse.fail();
    }

    @ExceptionHandler(value = BusinessException.class)
    public CommonResponse handleBusinessException(BusinessException e) {
        logger.error("系统异常错误信息:{}", e);
        return CommonResponse.fail();
    }

}
