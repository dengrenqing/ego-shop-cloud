package com.whsxt.feign.hystrix;

import com.whsxt.domain.Sku;
import com.whsxt.feign.BasketSkuFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@Component
@Slf4j
public class BasketSkuFeignHystrix implements BasketSkuFeign {
    /**
     * 远程调用根据skuIds查询sku的集合
     *
     * @param skuIds
     * @return
     */
    @Override
    public List<Sku> getSkuByIds(List<Long> skuIds) {
        log.error("远程调用根据skuIds查询sku的集合 失败");
        return null;
    }
}
