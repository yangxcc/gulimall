package com.yangxcc.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.gulimall.order.entity.MqMessageEntity;

import java.util.Map;

/**
 * 
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:16:38
 */
public interface MqMessageService extends IService<MqMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

