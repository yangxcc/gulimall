package com.yangxcc.gulimall.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.gulimall.logistics.entity.PurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:21:40
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

