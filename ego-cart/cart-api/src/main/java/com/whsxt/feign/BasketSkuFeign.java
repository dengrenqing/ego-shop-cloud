package com.whsxt.feign;

import com.whsxt.domain.Sku;
import com.whsxt.feign.hystrix.BasketSkuFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@FeignClient(value = "product-service", fallback = BasketSkuFeignHystrix.class)
public interface BasketSkuFeign {

    /**
     * 远程调用根据skuIds查询sku的集合
     *
     * @param skuIds
     * @return
     */
    @PostMapping("/prod/prod/getSkuByIds")
    List<Sku> getSkuByIds(@RequestBody List<Long> skuIds);

}
