package com.ruyuan.little.project.spring.jms;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:mq 生产者配置类
 **/
public class MqProducerConfiguration {

    /**
     * 日志管理组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MqProducerConfiguration.class);

    /**
     * 生产者group名称
     */
    private String producerGroupName;

    /**
     * rocketmq的namesrv的地址
     */
    private String nameServerAddr;

    /**
     * 生产者
     */
    private DefaultMQProducer producer;

    public MqProducerConfiguration(String producerGroupName, String nameServerAddr) {
        this.producerGroupName = producerGroupName;
        this.nameServerAddr = nameServerAddr;
    }

    /**
     * bean初始化执行方法
     *
     * @throws Exception
     */
    public void init() throws Exception {
        LOGGER.info("开始启动消息生产者服务...");

        //创建一个消息生产者，并设置一个消息生产者组
        producer = new DefaultMQProducer(producerGroupName);
        //指定 NameServer 地址
        producer.setNamesrvAddr(nameServerAddr);
        //初始化 SpringProducer，整个应用生命周期内只需要初始化一次
        producer.start();

        LOGGER.info("消息生产者服务启动成功.");
    }

    /**
     * bean消费时执行方法
     */
    public void destroy() {
        LOGGER.info("开始关闭消息生产者服务...");

        if (producer != null) {
            producer.shutdown();
        }

        LOGGER.info("消息生产者服务已关闭.");
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }
}