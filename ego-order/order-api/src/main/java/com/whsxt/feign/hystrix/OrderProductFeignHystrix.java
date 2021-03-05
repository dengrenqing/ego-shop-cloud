package com.whsxt.feign.hystrix;

import com.whsxt.domain.Sku;
import com.whsxt.feign.OrderProductFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author 武汉尚学堂
 */
@Component
@Slf4j
public class OrderProductFeignHystrix implements OrderProductFeign {
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

    /**
     * 修改库存的方法
     *
     * @param stockMap
     */
    @Override
    public void changeStock(Map<String, Map<Long, Integer>> stockMap) {
        log.error("远程调用修改库存的方法 失败");
    }
}
