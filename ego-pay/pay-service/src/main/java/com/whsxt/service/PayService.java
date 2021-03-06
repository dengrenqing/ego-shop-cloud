package com.whsxt.service;

import com.whsxt.domain.OrderSettlement;

import java.util.Map;

/**
 * @Author 武汉尚学堂
 * 接口就是规范
 * 深刻理解java面向对象，对集合，线程，网络编程等有自己的理解
 */
public interface PayService {


    /**
     * 调用支付生成二维码的接口
     *
     * @return
     */
    String pay(OrderSettlement orderSettlement);


    /**
     * 验证签名是否正确
     *
     * @param map
     * @return
     */
    Boolean checkRsa2(Map<String, String> map);

    /**
     * 支付成功 修改订单状态的接口
     *
     * @param orderNum
     */
    void paySuccessChangeStatus(String orderNum);


}
