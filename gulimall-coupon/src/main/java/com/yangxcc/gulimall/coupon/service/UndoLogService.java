package com.yangxcc.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangxcc.common.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:25:40
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

