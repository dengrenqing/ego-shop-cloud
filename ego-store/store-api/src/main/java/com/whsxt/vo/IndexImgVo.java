package com.whsxt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 武汉尚学堂
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("前台轮播图的实体类")
public class IndexImgVo {

    @ApiModelProperty(value = "图片")
    private String imgUrl;

    /**
     * 关联商品prodId
     */
    @ApiModelProperty(value = "关联商品prodId")
    private Long relation;


}
