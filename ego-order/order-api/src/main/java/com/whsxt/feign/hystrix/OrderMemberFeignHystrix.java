package com.whsxt.feign.hystrix;

import com.whsxt.domain.UserAddr;
import com.whsxt.feign.OrderMemberFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author 武汉尚学堂
 */
@Component
@Slf4j
public class OrderMemberFeignHystrix implements OrderMemberFeign {
    /**
     * 查询用户的默认收货地址
     *
     * @param openId
     * @return
     */
    @Override
    public UserAddr getDefaultAddr(String openId) {
        log.error("远程调用查询用户的默认收货地址 失败");
        return null;
    }
}
