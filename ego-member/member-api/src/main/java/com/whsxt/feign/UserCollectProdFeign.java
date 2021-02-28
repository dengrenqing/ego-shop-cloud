package com.whsxt.feign;

import com.whsxt.es.ProdEs;
import com.whsxt.feign.hystrix.UserCollectProdFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@FeignClient(value = "search-service", fallback = UserCollectProdFeignHystrix.class)
public interface UserCollectProdFeign {

    /**
     * 根据ids查询商品信息
     *
     * @param prodIds
     * @return
     */
    @PostMapping("/findProdEsByIds")
    List<ProdEs> findProdEsByIds(@RequestBody List<Long> prodIds);


}
