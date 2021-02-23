package com.whsxt.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * @Author 武汉尚学堂
 */
@Configuration
public class RequestRateLimitConfig {

    /**
     * ip限流
     *
     * @return
     */
    @Bean
    @Primary
    public KeyResolver ipAddrKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getHost().getHostString());
    }


    /**
     * 根据api 就是请求路径限流
     *
     * @return
     */
    @Bean
    public KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
    }

}
