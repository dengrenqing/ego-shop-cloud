package com.whsxt.vo;

import com.whsxt.domain.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单确认的入参对象")
public class OrderParam {

    @ApiModelProperty("购物车ids")
    private List<Long> basketIds;

    @ApiModelProperty("商品详情")
    private OrderItem orderItem;

    @ApiModelProperty("收货地址的id")
    private Integer addrId;

    @ApiModelProperty("优惠券")
    private List<Integer> couponIds;

    @ApiModelProperty("用户选择哪些优惠券")
    private Integer userChangeCoupon;

}
