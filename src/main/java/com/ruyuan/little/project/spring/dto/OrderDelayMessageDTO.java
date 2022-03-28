package com.ruyuan.little.project.spring.dto;

import com.ruyuan.little.project.spring.enums.DelayMessageTypeEnum;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单消息
 **/
public class OrderDelayMessageDTO {

    /**
     * 消息内容
     */
    private String content;

    /**
     * 订单消息推送类型 {@link DelayMessageTypeEnum}
     */
    private DelayMessageTypeEnum delayMessageType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DelayMessageTypeEnum getDelayMessageType() {
        return delayMessageType;
    }

    public void setDelayMessageType(DelayMessageTypeEnum delayMessageType) {
        this.delayMessageType = delayMessageType;
    }

    @Override
    public String toString() {
        return "OrderMessageDTO{" +
                "content='" + content + '\'' +
                ", delayMessageType=" + delayMessageType +
                '}';
    }
}