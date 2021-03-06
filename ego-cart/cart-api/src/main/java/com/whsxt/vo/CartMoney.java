package com.whsxt.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author 武汉尚学堂
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("购物车金额对象")
public class CartMoney {

    @ApiModelProperty("总金额")
    private BigDecimal totalMoney = new BigDecimal(0);

    @ApiModelProperty("优惠金额")
    private BigDecimal subtractMoney = new BigDecimal(0);

    @ApiModelProperty("最近金额")
    private BigDecimal finalMoney = new BigDecimal(0);


}
