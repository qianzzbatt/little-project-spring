package com.ruyuan.little.project.spring.event;

import com.alibaba.fastjson.JSON;
import com.ruyuan.little.project.spring.dto.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * {@link LogonEventPublisher}事件发布器
 *
 * @author little
 */

@Component
public class LogonEventPublisher implements ApplicationEventPublisherAware, ApplicationContextAware {

    /**
     * 日志管理组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogonEventPublisher.class);

    /**
     * 山下文容器
     */
    private ApplicationContext applicationContext;

    /**
     * 事件发布组件
     */
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 广播📢{@link LogonEventPublisher}事件
     *
     * @param consumer 消费者
     */
    public void publishEvent(Consumer consumer) {
        LOGGER.info("消费者事件发布开始，参数:{}", JSON.toJSONString(consumer));
        LogonEvent event = new LogonEvent(applicationContext);
        event.setConsumer(consumer);
        applicationEventPublisher.publishEvent(event);
        LOGGER.info("事件发布结束");
        LOGGER.info("broadcast FirstLogonEvent");
    }

}