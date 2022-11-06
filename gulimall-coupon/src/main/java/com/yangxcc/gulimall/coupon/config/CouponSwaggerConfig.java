package com.yangxcc.gulimall.coupon.config;

import com.yangxcc.common.swagger.SwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class CouponSwaggerConfig {
    /**
     * 新增Core模块
     *
     * @return
     */
    @Bean
    public Docket coreApi() {
        String moduleCode = "core";
        String moduleName = "核心模块";
        String[] basePackage = {"com.yangxcc.gulimall.coupon"};
        return SwaggerConfig.docket(moduleCode, moduleName, basePackage);
    }
}
