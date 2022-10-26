package com.yangxcc.gulimall.order.dao;

import com.yangxcc.gulimall.order.entity.RefundInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退款信息
 * 
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:16:38
 */
@Mapper
public interface RefundInfoDao extends BaseMapper<RefundInfoEntity> {
	
}
