package com.whsxt.vo;

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
@ApiModel("前台公告的vo")
public class NoticeVo {

    @ApiModelProperty(value = "公告标题")
    private String title;


}
