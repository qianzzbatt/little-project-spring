package com.ruyuan.little.project.spring.controller;

import com.ruyuan.little.project.common.dto.CommonResponse;
import com.ruyuan.little.project.common.dto.TableData;
import com.ruyuan.little.project.spring.dto.Consumer;
import com.ruyuan.little.project.spring.dto.ConsumerCoupon;
import com.ruyuan.little.project.spring.service.ConsumerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangquan
 * @Description
 * @Date 2022/3/23
 **/

@RestController
@RequestMapping("/consumer")
public class ConsumerController {


    @Resource
    private ConsumerService consumerService;

    @PostMapping("/login")
    public CommonResponse login(@RequestBody Consumer consumer){
        return CommonResponse.success(consumerService.login(consumer));
    }

    /**
     * 根据id查询消费者详情
     *
     * @param id 消费者id
     * @return 结果
     */
    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable String id) {
        return CommonResponse.success(consumerService.findById(Integer.parseInt(id)));
    }


    /**
     * 根据消费者id查找优惠券
     *
     * @param id 消费者id
     * @return 结果
     */
    @GetMapping("/allCoupon/{id}")
    public CommonResponse findCouponByConsumerId(@PathVariable Integer id) {
        List<ConsumerCoupon> consumerCoupons = consumerService.findCouponByConsumerId(id);

        return getCommonResponse(consumerCoupons);
    }


    /**
     * 根据消费者id和优惠券状态查找优惠券
     * @param id 消费者id
     * @param status 优惠券状态
     * @return 结果
     */
    @PostMapping("/coupon")
    public CommonResponse findCouponByConsumerIdAndStatus(Integer id,String status) {
        List<ConsumerCoupon> consumerCoupons = consumerService.findCouponByConsumerIdAndStatus(id,status);

        return getCommonResponse(consumerCoupons);
    }

    /**
     * 获取优惠券公共返回实体
     * @param consumerCoupons 用户优惠券
     * @return 结果
     */
    private CommonResponse getCommonResponse(List<ConsumerCoupon> consumerCoupons) {
        CommonResponse<TableData<ConsumerCoupon>> commonResponse = new CommonResponse<>();

        TableData<ConsumerCoupon> tableData = new TableData<>();
        tableData.setTotal((long) consumerCoupons.size());
        tableData.setRows(consumerCoupons);
        commonResponse.setData(tableData);

        return commonResponse;
    }

}
