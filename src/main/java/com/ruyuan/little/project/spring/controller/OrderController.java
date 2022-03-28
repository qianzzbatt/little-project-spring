package com.ruyuan.little.project.spring.controller;

import com.ruyuan.little.project.common.dto.CommonResponse;
import com.ruyuan.little.project.common.dto.TableData;
import com.ruyuan.little.project.spring.dto.Order;
import com.ruyuan.little.project.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单controller组件
 **/

@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * 订单service管理组件
     */
    @Resource
    private OrderService orderService;

    /**
     * 添加订单
     *
     * @param order 订单信息
     * @return 结果
     */
    @PostMapping("/add")
    public CommonResponse add(@Valid Order order) {
        orderService.createOrder(order);
        return CommonResponse.success(order);
    }

    /**
     * 分页查询订单列表
     *
     * @param order 订单信息
     * @return 结果
     */
    @PostMapping("/getPage")
    public CommonResponse getPage(Order order) {
        // 根据条件查询订单总条数
        int total = orderService.getPageTotal(order);

        // 封装响应数据表格
        CommonResponse<TableData<Order>> commonResponse = new CommonResponse<>();
        TableData<Order> tableData = new TableData<>();
        tableData.setTotal((long) total);
        if (total != 0) {
            // 条数不为空查询订单列表
            tableData.setRows(orderService.getPage(order));
        }
        commonResponse.setData(tableData);
        return commonResponse;
    }

    /**
     * 根据订单id查询订单信息
     *
     * @param id 订单id
     * @return 结果
     */
    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable Integer id) {
        return CommonResponse.success(orderService.findById(id));
    }


    /**
     * 撤销订单
     *
     * @param orderId 订单id
     * @return 结果
     */
    @RequestMapping("/cancel")
    public CommonResponse cancelOrder(Integer orderId) {
        return CommonResponse.success(orderService.cancelOrder(orderId));
    }

    /**
     * 支付订单
     *
     * @param orderId 订单id
     * @return 结果
     */
    @RequestMapping("/pay")
    public CommonResponse payOrder(Integer orderId) {
        return CommonResponse.success(orderService.payOrder(orderId));
    }

    /**
     * 结束订单
     *
     * @param orderId 订单id
     * @return 结果
     */
    @RequestMapping("/finish")
    public CommonResponse finishOrder(Integer orderId) {
        return CommonResponse.success(orderService.finishOrder(orderId));
    }

    /**
     * 订单发布评论
     *
     * @param orderId        订单id
     * @param score          评价
     * @param commentContent 评论信息
     * @return 结果
     */
    @RequestMapping("/comment")
    public CommonResponse publishComment(Integer orderId,
                                         Integer score,
                                         String commentContent) {
        int publishComment = orderService.publishComment(orderId, score, commentContent);
        return CommonResponse.success(orderId);
    }


}