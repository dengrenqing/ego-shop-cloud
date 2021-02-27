package com.whsxt.feign;

import com.whsxt.domain.Prod;
import com.whsxt.feign.hystrix.IndexImgProdFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author 武汉尚学堂
 */
@FeignClient(value = "product-service", fallback = IndexImgProdFeignHystrix.class)
public interface IndexImgProdFeign {

    /**
     * 远程调用根据id查询商品信息
     *
     * @param prodId
     * @return
     */
    @GetMapping("/prod/prod/findProdById")
    Prod findProdById(@RequestParam("prodId") Long prodId);

}