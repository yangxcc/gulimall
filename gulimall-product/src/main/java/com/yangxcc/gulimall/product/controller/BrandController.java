package com.yangxcc.gulimall.product.controller;

import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.common.utils.R;
import com.yangxcc.common.validgroup.addGroup;
import com.yangxcc.common.validgroup.updateGroup;
import com.yangxcc.gulimall.product.entity.BrandEntity;
import com.yangxcc.gulimall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



/**
 * 品牌
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-28 10:27:54
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated(value = addGroup.class) @RequestBody BrandEntity brand, BindingResult result){
        if (result.hasErrors()) {
            HashMap<String, String> errorMsg = new HashMap<>();
            result.getFieldErrors().forEach((item) -> {
                String field = item.getField();
                String message = item.getDefaultMessage();
                errorMsg.put(field, message);
            });
            return R.error().put("data", errorMsg);

        } else {
            brandService.save(brand);
            return R.ok();
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated(value = updateGroup.class) @RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
