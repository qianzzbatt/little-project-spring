package com.ruyuan.little.project.spring.service;

import com.ruyuan.little.project.spring.dto.Consumer;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单事件通知组件
 **/
public interface LoginEventManager {

    /**
     * 用户登录事件
     *
     * @param consumer 用户信息
     */
    void loginEvent(Consumer consumer);

}