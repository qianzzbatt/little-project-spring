package com.ruyuan.little.project.spring.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruyuan.little.project.common.dto.CommonResponse;
import com.ruyuan.little.project.spring.constants.StringPoolConstant;
import com.ruyuan.little.project.spring.expection.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author huangquan
 * @Description 记录接口日志信息
 * @Date 2022/3/22
 **/

@Aspect
@Component
public class ControllerLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.ruyuan.little.project.spring.controller.*.*(..))")
    public void pointcut() {
    }

    /**
     * 环绕通知，在方法执行前后
     *
     * @param point 切入点
     * @return 结果
     * @throws BusinessException
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws BusinessException {
        // 签名信息
        Signature signature = point.getSignature();
        // 强转为方法信息
        MethodSignature methodSignature = (MethodSignature) signature;
        // 参数名称
        String[] parameterNames = methodSignature.getParameterNames();
        //执行的对象
        Object target = point.getTarget();

        logger.info("请求处理方法:{}", target.getClass().getSimpleName() + StringPoolConstant.DOT + methodSignature.getMethod().getName());
        Object[] parameterValues = point.getArgs();
        //查看入参
        logger.info("请求参数名:{}，请求参数值:{}", JSONObject.toJSONString(parameterNames), JSONObject.toJSONString(parameterValues));
        try {
            // 开始时间
            long startTime = System.currentTimeMillis();

            // 执行方法
            Object response = point.proceed();

            // 结束时间
            long endTime = System.currentTimeMillis();
            logger.info("请求处理时间差:{}, 响应结果:{}", endTime - startTime, JSON.toJSONString(response));
            return response;
        } catch (Throwable throwable) {
            logger.error("执行方法:{}失败，异常信息:{}", methodSignature.getMethod().getName(), throwable);
            if (throwable instanceof BusinessException) {
                // 业务异常
                return CommonResponse.fail();
            }
            // 非业务异常，封装一层异常
            throw new BusinessException("方法 " + methodSignature.getMethod().getName() + " 执行失败");
        }
    }
}
