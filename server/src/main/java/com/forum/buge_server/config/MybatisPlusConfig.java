package com.forum.buge_server.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @date 2020-05-16
 **/
@Configuration
@MapperScan("com.forum.buge_server.mapper*")
public class MybatisPlusConfig {
    /**
     * 加载分页插件
     * 
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
