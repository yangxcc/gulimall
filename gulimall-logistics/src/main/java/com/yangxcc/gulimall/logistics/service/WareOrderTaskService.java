package com.yangxcc.gulimall.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.gulimall.logistics.entity.WareOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:21:40
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

