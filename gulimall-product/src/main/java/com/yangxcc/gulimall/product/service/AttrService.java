package com.yangxcc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.gulimall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-28 10:27:54
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

