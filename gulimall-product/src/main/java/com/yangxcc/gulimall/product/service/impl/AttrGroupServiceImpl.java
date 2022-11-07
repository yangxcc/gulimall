package com.yangxcc.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.common.utils.Query;
import com.yangxcc.gulimall.product.dao.AttrGroupDao;
import com.yangxcc.gulimall.product.entity.AttrGroupEntity;
import com.yangxcc.gulimall.product.service.AttrGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCategoryId(Map<String, Object> params, Long catelogID) {
        IPage<AttrGroupEntity> page = null;
        if (catelogID == 0) {
            page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    new QueryWrapper<AttrGroupEntity>());
        } else {
            // select * from pms_attr_group where catelog_id=catelogID and (attr_group_id=key or attr_group_name like %key%)
            Object key = params.get("key");
            QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogID);
            if (StringUtils.hasLength((CharSequence) key)) {
                wrapper.and(((obj) -> {
                    obj.eq("attr_group_id", key).or().like("attr_group_name", key);
                }));

                page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            }
        }
        return new PageUtils(page);
    }

}