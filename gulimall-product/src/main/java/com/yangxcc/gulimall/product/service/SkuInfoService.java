package com.yangxcc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.gulimall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 20:32:23
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

