package com.whsxt.feign;

import com.whsxt.domain.UserAddr;
import com.whsxt.feign.hystrix.OrderMemberFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author 武汉尚学堂
 */
@FeignClient(value = "member-service", fallback = OrderMemberFeignHystrix.class)
public interface OrderMemberFeign {

    /**
     * 查询用户的默认收货地址
     *
     * @param openId
     * @return
     */
    @GetMapping("p/address/getDefaultAddr")
    UserAddr getDefaultAddr(@RequestParam("openId") String openId);


}