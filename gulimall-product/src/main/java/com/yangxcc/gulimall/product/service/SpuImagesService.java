package com.yangxcc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.gulimall.product.entity.SpuImagesEntity;

import java.util.Map;

/**
 * spu图片
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 20:32:23
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

