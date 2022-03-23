package com.ruyuan.little.project.spring.event;

import com.ruyuan.little.project.spring.dto.Consumer;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:spring实战  定义首次登录事件
 **/

public class LogonEvent extends ApplicationEvent {

    /**
     * 消费者实体
     */
    private Consumer consumer;

    public LogonEvent(Object source, Consumer consumer) {
        super(source);
        this.consumer = consumer;
    }

    public LogonEvent(@NonNull Object source) {
        super(source);
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}