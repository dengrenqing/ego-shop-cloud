package com.whsxt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * @Author 武汉尚学堂
 * 1. token的存储
 * 2. jwt的转换器
 * 3. 第三方应用
 * 4. endpoints暴露
 */
@Configuration
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 因为common有这个bean对象了 我就不需要了
     *
     * @return
     */
//    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 直接使用非对称加密的方式实现
     *
     * @return
     */
//    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 把私钥读到内存中
        ClassPathResource resource = new ClassPathResource("cxs-jwt.jks");
        // 创建一个钥匙工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "cxs123".toCharArray());
        // 拿到钥匙
        KeyPair privateKey = keyStoreKeyFactory.getKeyPair("cxs-jwt");
        // 设置进转换器里面
        jwtAccessTokenConverter.setKeyPair(privateKey);
        return jwtAccessTokenConverter;
    }

    /**
     * 配置第三方应用
     * password 只要是登录都用这个授权方式
     * 客户端授权  用于微服务之间自发的进行远程调用时 资源服务器必须要token的情况 当然也是可以放行服务提供者的接口的
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("web")
                .secret(passwordEncoder.encode("web-secret"))
                .scopes("all")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(7200)
                .redirectUris("https://www.baidu.com")
                .and()
                .withClient("client")
                .secret(passwordEncoder.encode("client-secret"))
                .scopes("read")
                .authorizedGrantTypes("client_credentials")
                .accessTokenValiditySeconds(Integer.MAX_VALUE) // 66 年
                .redirectUris("https://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager);
    }
}
