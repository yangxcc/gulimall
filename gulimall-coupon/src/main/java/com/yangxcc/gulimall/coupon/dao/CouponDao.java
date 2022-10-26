package com.yangxcc.gulimall.coupon.dao;

import com.yangxcc.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:25:40
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
