package com.whsxt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author 武汉尚学堂
 */
@Data
@ConfigurationProperties(prefix = "wx")
public class WxProperties {

    private String appId;

    private String appSecret;

    private String accessTokenUrl;

    private String sendMsgUrl;


}
