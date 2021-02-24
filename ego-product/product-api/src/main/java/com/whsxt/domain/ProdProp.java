package com.whsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 武汉尚学堂
 * 
 */
@ApiModel(value="com-whsxt-domain-ProdProp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "prod_prop")
public class ProdProp implements Serializable {
    /**
     * 属性id
     */
    @TableId(value = "prop_id", type = IdType.AUTO)
    @ApiModelProperty(value="属性id")
    private Long propId;

    /**
     * 属性名称
     */
    @TableField(value = "prop_name")
    @ApiModelProperty(value="属性名称")
    private String propName;

    /**
     * ProdPropRule 1:销售属性(规格); 2:参数属性;
     */
    @TableField(value = "rule")
    @ApiModelProperty(value="ProdPropRule 1:销售属性(规格); 2:参数属性;")
    private Byte rule;

    /**
     * 店铺id
     */
    @TableField(value = "shop_id")
    @ApiModelProperty(value="店铺id")
    private Long shopId;

    private static final long serialVersionUID = 1L;

    public static final String COL_PROP_ID = "prop_id";

    public static final String COL_PROP_NAME = "prop_name";

    public static final String COL_RULE = "rule";

    public static final String COL_SHOP_ID = "shop_id";
}