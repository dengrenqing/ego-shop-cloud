package com.whsxt.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author 武汉尚学堂
 * 开启资源服务器
 * 统一做jwt的解析
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级别的验证
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 做公钥的解析
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 设置公钥
        ClassPathResource resource = new ClassPathResource("publicKey.txt");
        String publicKey = null;
        try {
            publicKey = FileUtil.readString(resource.getFile(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置公钥
        jwtAccessTokenConverter.setVerifierKey(publicKey);
        return jwtAccessTokenConverter;
    }

    /**
     * 设置资源
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore());
    }

    /**
     * 配置放行的路径 所有的微服务都依赖common 就不需要配置了
     * 放行swagger路径
     * http的设置
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 因为使用jwt的方式 session就不需要了
        http.sessionManagement().disable();
        http.authorizeRequests().antMatchers(
                "/v2/api-docs",
                "/v3/api-docs",
                "/swagger-resources/configuration/ui",  //用来获取支持的动作
                "/swagger-resources",                   //用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/webjars/**",
                "/swagger-ui/**",
                "/druid/**",
                "/actuator/**",
                "/payNotify/**" // 支付宝调用我们 相当于就是正常的客户端请求服务器 要放行这个接口
        ).permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .headers()
                .cacheControl(); // 控制请求头的缓存
    }
}
