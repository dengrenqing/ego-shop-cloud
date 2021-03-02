package com.whsxt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author 武汉尚学堂
 */
@Data
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    private String accessKeyId;

    private String AccessKeySecret;

    private String version;


}
