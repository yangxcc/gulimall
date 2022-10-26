package com.yangxcc.gulimall.member.dao;

import com.yangxcc.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author yangx
 * @email yangx@gmail.com
 * @date 2022-10-26 21:19:18
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
