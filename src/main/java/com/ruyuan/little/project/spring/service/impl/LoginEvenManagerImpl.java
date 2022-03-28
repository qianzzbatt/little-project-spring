package com.ruyuan.little.project.spring.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruyuan.little.project.spring.dto.Consumer;
import com.ruyuan.little.project.spring.jms.LoginProducerConfiguration;
import com.ruyuan.little.project.spring.service.LoginEventManager;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单事件管理组件
 **/
@Service
public class LoginEvenManagerImpl implements LoginEventManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginEvenManagerImpl.class);

    /**
     * mq的配置类
     */
    @Autowired
    private LoginProducerConfiguration loginProducerConfiguration;

    /**
     * 登录topic
     */
    @Value("${rocketmq.login.topic}")
    private String loginTopic;

    @Override
    public void loginEvent(Consumer consumer) {
        this.sendLoginMessage(consumer);
    }

    /**
     * 发布登录信息
     *
     * @param consumer 用户信息
     */
    private void sendLoginMessage(Consumer consumer) {
        Message message = new Message();
        message.setTopic(loginTopic);
        message.setBody(JSON.toJSONString(consumer).getBytes(StandardCharsets.UTF_8));
        try {
            loginProducerConfiguration.getProducer().send(message);
            LOGGER.info("send login message finished consumer:{}", consumer);
        } catch (Exception e) {
            // 发送登录消息失败
            LOGGER.error("send login message fail,error message:{}", e.getMessage());
        }
    }

}