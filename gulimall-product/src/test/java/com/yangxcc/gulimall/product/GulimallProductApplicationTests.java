package com.yangxcc.gulimall.product;

import com.yangxcc.gulimall.product.entity.SkuInfoEntity;
import com.yangxcc.gulimall.product.service.SkuInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    SkuInfoService skuInfoService;

    @Test
    void contextLoads() {
        SkuInfoEntity entity = new SkuInfoEntity();
        entity.setPrice(new BigDecimal("12.0"));
        skuInfoService.save(entity);
        System.out.println("插入成功");
    }
}
