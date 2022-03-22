package com.ruyuan.little.project.spring.controller;

import com.ruyuan.little.project.common.dto.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:健康检查的controller
 **/
@RestController
public class HealthController {

    @GetMapping(value = "/index")
    public CommonResponse health() {
        return CommonResponse.success();
    }
}