package com.yangxcc.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
* open-feign远程调用的使用步骤：
 * 1. 导入依赖包：spring-cloud-starter-openfeign、spring-cloud-starter-loadbalancer（这一个必须要有，不然会有报错）
 * 2. 在调用类中编写一个接口，告诉springcloud这个接口需要远程调用，详情见com.yangxcc.gulimall.coupon.feign
 * 3. 开启远程调用功能 @EnableFeignClients(basePackages = "com.yangxcc.gulimall.coupon.feign")
* */
@EnableFeignClients(basePackages = "com.yangxcc.gulimall.coupon.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
