package com.whsxt.feign;

import com.whsxt.domain.User;
import com.whsxt.feign.hystrix.ProdCommMemberFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@FeignClient(value = "member-service", fallback = ProdCommMemberFeignHystrix.class)
public interface ProdCommMemberFeign {

    /**
     * 远程调用获取用户信息集合
     *
     * @param userIds
     * @return
     */
    @PostMapping("/p/findUserInfoByUserIds")
    List<User> findUserInfoByUserIds(@RequestBody List<String> userIds);





}
