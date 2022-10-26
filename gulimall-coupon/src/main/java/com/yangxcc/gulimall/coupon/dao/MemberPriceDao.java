package com.yangxcc.gulimall.coupon.dao;

import com.yangxcc.gulimall.coupon.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 * 
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:25:40
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {
	
}
