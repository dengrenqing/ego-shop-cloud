package com.whsxt.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 武汉尚学堂
 */
@Configuration
@MapperScan(basePackages = {"com.whsxt.mapper"})
public class MybatisPlusConfig {

    /**
     * 做分页
     *
     * @return
     */
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
