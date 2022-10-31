package com.yangxcc.gulimall.coupon.controller;

import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.common.utils.R;
import com.yangxcc.gulimall.coupon.entity.CouponEntity;
import com.yangxcc.gulimall.coupon.feign.ProductFeignService;
import com.yangxcc.gulimall.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;


/**
 * 优惠券信息
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:25:40
 */
@RefreshScope  // 动态刷新配置，但是现在默认就是动态刷新的
@RestController
@RequestMapping("coupon/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @Autowired
    ProductFeignService productFeignService;

    @RequestMapping("/list/sku_info")
    public R testFeign() {
        R product = productFeignService.listProduct();
        return R.ok().put("shuInfo", product);
    }

    @Value("${coupon.user.name}")
    private String name;
    @Value("${coupon.user.age}")
    private Integer age;

    @RequestMapping("/test/nacos_configure")
    public R testNacosConfigure() {
        return Objects.requireNonNull(R.ok().put("name", name)).put("age", age);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
