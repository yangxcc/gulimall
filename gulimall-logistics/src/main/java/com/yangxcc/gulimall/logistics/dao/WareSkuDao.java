package com.yangxcc.gulimall.logistics.dao;

import com.yangxcc.gulimall.logistics.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:21:40
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
