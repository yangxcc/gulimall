package com.yangxcc.gulimall.coupon.feign;

import com.yangxcc.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-product")
public interface ProductFeignService {
    @RequestMapping("product/skuinfo/list/product")
    public R listProduct();
}
