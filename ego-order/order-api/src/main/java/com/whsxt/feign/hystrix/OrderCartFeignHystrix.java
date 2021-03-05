package com.whsxt.feign.hystrix;

import com.whsxt.domain.Basket;
import com.whsxt.feign.OrderCartFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@Component
@Slf4j
public class OrderCartFeignHystrix implements OrderCartFeign {
    /**
     * 远程调用购物车 拿到购物车的商品集合
     *
     * @param basketIds
     * @return
     */
    @Override
    public List<Basket> getBasketByIds(List<Long> basketIds) {
        log.error("远程调用购物车 拿到购物车的商品集合 失败");
        return null;
    }

    /**
     * 远程调用根据用户id和skuIds清空购物车
     *
     * @param openId
     * @param skuIds
     */
    @Override
    public void clearCart(String openId, List<Long> skuIds) {
        log.error("远程调用根据用户id和skuIds清空购物车 失败");
    }
}
