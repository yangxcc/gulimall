package com.yangxcc.gulimall.product;

import com.yangxcc.gulimall.product.entity.CategoryEntity;
import com.yangxcc.gulimall.product.entity.SkuInfoEntity;
import com.yangxcc.gulimall.product.service.CategoryService;
import com.yangxcc.gulimall.product.service.SkuInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testCategory() {
        CategoryEntity categoryPathById = categoryService.getCategoryPathById((long) 996);
        System.out.println(categoryPathById);
    }

    @Test
    void contextLoads() {
        SkuInfoEntity entity = new SkuInfoEntity();
        entity.setPrice(new BigDecimal("12.0"));
        skuInfoService.save(entity);
        System.out.println("插入成功");
    }

    @Test
    public void testRedis() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // 保存
        ops.set("hello", "world_"+ UUID.randomUUID().toString());
        // 查询
        String v = ops.get("hello");
        System.out.println("测试数据是："+v);
    }
}
