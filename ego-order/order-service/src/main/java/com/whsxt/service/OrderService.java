package com.whsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whsxt.vo.OrderStatusResult;

/**
 * @Author 武汉尚学堂
 */
public interface OrderService extends IService<Order> {


    /**
     * 查询当前用户的订单数量
     *
     * @param openId
     * @return
     */
    OrderStatusResult findOrderStatus(String openId);

    /**
     * 分页查询当前用户的订单
     *
     * @param page
     * @param order
     * @return
     */
    Page<Order> findOrderPage(Page<Order> page, Order order);
}
