package com.yangxcc.gulimall.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.gulimall.logistics.entity.WareSkuEntity;

import java.util.Map;

/**
 * εεεΊε­
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:21:40
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

