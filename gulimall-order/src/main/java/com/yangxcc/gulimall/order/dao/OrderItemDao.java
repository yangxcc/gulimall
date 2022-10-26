package com.yangxcc.gulimall.order.dao;

import com.yangxcc.gulimall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:16:38
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
