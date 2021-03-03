package com.whsxt.service;

import com.whsxt.domain.Basket;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whsxt.vo.CartMoney;
import com.whsxt.vo.ShopCartResult;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
public interface BasketService extends IService<Basket> {


    /**
     * 查询购物车商品数量
     *
     * @param openId
     * @return
     */
    Integer findCartCount(String openId);

    /**
     * 修改购物车商品数量
     *
     * @param basket
     */
    void changeItem(Basket basket);

    /**
     * 查询购物车详情
     *
     * @param openId
     * @return
     */
    List<ShopCartResult> getCartInfo(String openId);

    /**
     * 计算购物车选中商品的总金额
     *
     * @param basketIds
     * @return
     */
    CartMoney calcMoney(List<Long> basketIds);
}
