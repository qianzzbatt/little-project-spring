package com.ruyuan.little.project.spring.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ruyuan.little.project.common.dto.CommonResponse;
import com.ruyuan.little.project.spring.constants.StringPoolConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author huangquan
 * @Description  判断是否登录拦截器，前端会在发送请求时传递手机号phoneNumber
 * @Date 2022/3/22
 **/
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 判断请求中是否包含手机号，先从请求体中获取
/*        String phoneNumber = request.getParameter("phoneNumber");
        if (StringUtils.isEmpty(phoneNumber)) {
            // 再从请求头中获取
            phoneNumber = request.getHeader("phoneNumber");
            if (StringUtils.isEmpty(phoneNumber)) {
                logger.info("请求接口： {} 中不包含手机号", request.getRequestURI());
                //获取OutputStream输出流
                try (OutputStream outputStream = response.getOutputStream()) {
                    //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
                    response.setHeader("content-type", "application/json");
                    //将字符转换成字节数组，指定以UTF-8编码进行转换
                    byte[] dataByteArr = JSONObject.toJSONString(CommonResponse.fail()).getBytes(StandardCharsets.UTF_8);
                    //使用OutputStream流向客户端输出字节数组
                    outputStream.write(dataByteArr);
                } catch (IOException e) {
                    logger.error("LoginInterceptor exception", e);
                }
                return false;
            }
        }*/
        logger.debug("LoginInterceptor.preHandle()");
        return true;
    }
}
