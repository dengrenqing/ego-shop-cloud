package com.whsxt.feign.hystrix;

import com.whsxt.domain.Prod;
import com.whsxt.feign.IndexImgProdFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author 武汉尚学堂
 */
@Component
@Slf4j
public class IndexImgProdFeignHystrix implements IndexImgProdFeign {
    /**
     * 远程调用根据id查询商品信息
     *
     * @param prodId
     * @return
     */
    @Override
    public Prod findProdById(Long prodId) {
        log.error("远程调用失败了");
        return null;
    }
}