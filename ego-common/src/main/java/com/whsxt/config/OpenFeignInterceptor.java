package com.whsxt.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 武汉尚学堂
 * token的传递问题
 * 远程调用的拦截
 */
@Configuration
public class OpenFeignInterceptor implements RequestInterceptor {


    /**
     * 在这里做token的传递
     * 1. 浏览器-- A服务 ---B服务
     * 2. mq直接发起远程调用
     * 3. 支付宝回调你 有request 但是没有token
     *
     * @param template 发起远程调用的请求
     */
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 是从浏览器过来的request  肯定有token的
        HttpServletRequest request = requestAttributes.getRequest();
        if (!ObjectUtils.isEmpty(request)) {
            String authorization = request.getHeader("Authorization");
            if (!StringUtils.isEmpty(authorization)) {
                // 往新的请求里面放 做一个传递
                template.header("Authorization", authorization);
            }else {
                // 支付宝回调你的 有request 但是没有token
                // 设置一个永久的token 保证他可以正常调用其他服务
                template.header("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIl0sImV4cCI6Mzc2MTU1NjcwNiwianRpIjoiNjc3YTA0YzMtMDZhMC00YTZkLTlmZDgtNDkzMzVmN2MwNDY4IiwiY2xpZW50X2lkIjoiY2xpZW50In0.Kqa83oWTtIfnBFFjWueDX444MA2l9PQfRyNDfFmfkqXIftUwZCHQRVq4hJkYR2l8o5H8i1FkjCJzO6gkf8WtedtOBtyeqaV_HNcuwA4hkvgh59TcicKWs6ar_fFlzOlVZ8uGRNM-CWZpcMchYfeHLDfyVBEgFv3CMZHZ82gUcVwyi5vSHre20QKo6CVgRqTwvLKfchSmJd46fz49vJBG9mY9xb6FMnjwDRaCXUXhGVLbsxMeRTW5yUsa7UdLYnkpK8Nlk8nNYKN_O1KH7u_1-9QJVCI5N8qZGGb-51KaPtjjtBJ-NitMyGVboS5wCd5sCtyPOlkHUdyNvW_CAQWP4g");
            }
        } else {
            // 设置一个永久的token 保证他可以正常调用其他服务
            template.header("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIl0sImV4cCI6Mzc2MTU1NjcwNiwianRpIjoiNjc3YTA0YzMtMDZhMC00YTZkLTlmZDgtNDkzMzVmN2MwNDY4IiwiY2xpZW50X2lkIjoiY2xpZW50In0.Kqa83oWTtIfnBFFjWueDX444MA2l9PQfRyNDfFmfkqXIftUwZCHQRVq4hJkYR2l8o5H8i1FkjCJzO6gkf8WtedtOBtyeqaV_HNcuwA4hkvgh59TcicKWs6ar_fFlzOlVZ8uGRNM-CWZpcMchYfeHLDfyVBEgFv3CMZHZ82gUcVwyi5vSHre20QKo6CVgRqTwvLKfchSmJd46fz49vJBG9mY9xb6FMnjwDRaCXUXhGVLbsxMeRTW5yUsa7UdLYnkpK8Nlk8nNYKN_O1KH7u_1-9QJVCI5N8qZGGb-51KaPtjjtBJ-NitMyGVboS5wCd5sCtyPOlkHUdyNvW_CAQWP4g");
        }
    }
}
