package com.whsxt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Order;
import com.whsxt.service.OrderService;
import com.whsxt.vo.OrderStatusResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 武汉尚学堂
 */
@RestController
@Api("订单管理接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/p/myOrder/orderCount")
    @ApiOperation("查询当前用户的订单数量")
    public ResponseEntity<OrderStatusResult> findOrderCount() {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        OrderStatusResult orderStatusResult = orderService.findOrderStatus(openId);
        return ResponseEntity.ok(orderStatusResult);
    }

    @GetMapping("/p/myOrder/myOrder")
    @ApiOperation("分页查询当前用户的订单")
    public ResponseEntity<Page<Order>> myOrderPage(Page<Order> page, Order order) {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        order.setUserId(openId);
        Page<Order> orderPage = orderService.findOrderPage(page, order);
        return ResponseEntity.ok(orderPage);
    }


}
