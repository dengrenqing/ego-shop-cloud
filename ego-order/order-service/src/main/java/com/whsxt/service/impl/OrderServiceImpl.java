package com.whsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.OrderItem;
import com.whsxt.mapper.OrderItemMapper;
import com.whsxt.vo.OrderStatusResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.OrderMapper;
import com.whsxt.domain.Order;
import com.whsxt.service.OrderService;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    /**
     * 查询当前用户的订单数量
     *
     * @param openId
     * @return
     */
    @Override
    public OrderStatusResult findOrderStatus(String openId) {
        // 用户的订单也是比较多的
        Integer unPay = orderMapper.selectCount(new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, openId)
                .eq(Order::getStatus, 1)
        );
        Integer payed = orderMapper.selectCount(new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, openId)
                .eq(Order::getStatus, 2)
        );
        Integer consignment = orderMapper.selectCount(new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, openId)
                .eq(Order::getStatus, 3)
        );
        OrderStatusResult orderStatusResult = new OrderStatusResult();
        orderStatusResult.setPayed(payed);
        orderStatusResult.setUnPay(unPay);
        orderStatusResult.setConsignment(consignment);
        return orderStatusResult;
    }

    /**
     * 分页查询当前用户的订单
     *
     * @param page
     * @param order
     * @return
     */
    @Override
    public Page<Order> findOrderPage(Page<Order> page, Order order) {
        // 首先查询订单表  orderNum 再查询订单条目表 List<orderItem> 组装数据 返回
        Page<Order> orderPage = orderMapper.selectPage(page, new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, order.getUserId())
                .eq(order.getStatus() != 0, Order::getStatus, order.getStatus())
        );
        // 1 2
        List<Order> orderList = orderPage.getRecords();

        // 订单和条目表是一个 一对多的关系 orderNum
        List<String> orderNumList = orderList.stream()
                .map(Order::getOrderNumber)
                .collect(Collectors.toList());
        // 查询订单条目表 1 2 3 4
        List<OrderItem> orderItemList = orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>()
                .in(OrderItem::getOrderNumber, orderNumList)
        );
        // 循环 组装数据了
        orderList.forEach(order1 -> {
            List<OrderItem> orderItems = orderItemList.stream()
                    .filter(orderItem -> orderItem.getOrderNumber().equals(order1.getOrderNumber()))
                    .collect(Collectors.toList());
            order1.setOrderItemDtos(orderItems);
        });
        return orderPage;
    }
}
