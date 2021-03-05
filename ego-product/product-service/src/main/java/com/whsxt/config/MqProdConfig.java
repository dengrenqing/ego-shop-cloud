package com.whsxt.config;

import com.whsxt.constant.QueueConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 武汉尚学堂
 */
@Configuration
public class MqProdConfig {


    @Bean
    public Queue prodChangeQueue() {
        return new Queue(QueueConstant.PROD_CHANGE_QUEUE);
    }

    @Bean
    public DirectExchange prodChangeEx() {
        return new DirectExchange(QueueConstant.PROD_CHANGE_EX);
    }

    @Bean
    public Binding prodChangeBind() {
        return BindingBuilder.bind(prodChangeQueue()).to(prodChangeEx()).with(QueueConstant.PROD_CHANGE_KEY);
    }

}
