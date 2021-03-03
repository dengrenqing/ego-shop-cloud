package com.whsxt.controller;

import com.whsxt.domain.Basket;
import com.whsxt.service.BasketService;
import com.whsxt.vo.CartMoney;
import com.whsxt.vo.ShopCartResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@RestController
@Api("购物车管理接口")
public class CartController {


    @Autowired
    private BasketService basketService;


    @GetMapping("p/shopCart/prodCount")
    @ApiOperation("查询购物车商品数量")
    public ResponseEntity<Integer> getCartCount() {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Integer count = basketService.findCartCount(openId);
        return ResponseEntity.ok(count);
    }


    @PostMapping("p/shopCart/changeItem")
    @ApiOperation("修改购物车商品数量")
    public ResponseEntity<Void> changeItem(@RequestBody Basket basket) {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        basket.setUserId(openId);
        basketService.changeItem(basket);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/p/shopCart/info")
    @ApiOperation("查询购物车详情")
    public ResponseEntity<List<ShopCartResult>> changeItem() {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        List<ShopCartResult> shopCartResults = basketService.getCartInfo(openId);
        return ResponseEntity.ok(shopCartResults);
    }


    @DeleteMapping("/p/shopCart/deleteItem")
    @ApiOperation("删除购物车选中商品")
    public ResponseEntity<Void> cleanCart(@RequestBody List<Long> basketIds) {
        basketService.removeByIds(basketIds);
        return ResponseEntity.ok().build();
    }


    /**
     * 总金额 是多少
     * 满减是多少
     * 优惠是多少
     * 最终是多少
     *
     * @param basketIds
     * @return
     */
    @PostMapping("p/shopCart/totalPay")
    @ApiOperation("计算购物车选中商品的总金额")
    public ResponseEntity<CartMoney> totalPay(@RequestBody List<Long> basketIds) {
        CartMoney cartMoney = basketService.calcMoney(basketIds);
        return ResponseEntity.ok(cartMoney);
    }


}
