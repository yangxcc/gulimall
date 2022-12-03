package com.yangxcc.gulimall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.yangxcc.gulimall.product.dao")
public class MyBatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor interceptor = new PaginationInterceptor();
        // 当请求的分页大于最大页后操作，true调回首页，false继续请求
        interceptor.setOverflow(true);
        // 单页的最大限制数量
        interceptor.setLimit(100);
        return interceptor;
    }
}
