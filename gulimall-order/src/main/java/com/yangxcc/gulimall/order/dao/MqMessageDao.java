package com.yangxcc.gulimall.order.dao;

import com.yangxcc.gulimall.order.entity.MqMessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:16:38
 */
@Mapper
public interface MqMessageDao extends BaseMapper<MqMessageEntity> {
	
}
