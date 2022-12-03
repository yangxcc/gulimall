package com.yangxcc.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangxcc.common.utils.PageUtils;
import com.yangxcc.common.utils.Query;
import com.yangxcc.gulimall.product.dao.CategoryDao;
import com.yangxcc.gulimall.product.entity.CategoryEntity;
import com.yangxcc.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listTree() {
        String categoryTree = stringRedisTemplate.opsForValue().get("category_tree");
        if (!StringUtils.hasLength(categoryTree)) {
            List<CategoryEntity> data = listTreeFromDB();
            // 将数据转成json格式的字符串
            String str = JSON.toJSONString(data);
            stringRedisTemplate.opsForValue().set("category_tree", str);
            return data;
        }

        // 将categoryTree中的数据反序列化成string
        return JSON.parseObject(categoryTree, new TypeReference<List<CategoryEntity>>(){});
    }

    public List<CategoryEntity> listTreeFromDB() {
        // 查出全部category记录
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        // 获得一级目录
        List<CategoryEntity> entities = categoryEntities.stream().filter(
                categoryEntity -> categoryEntity.getParentCid() == 0
        ).map((menu -> {
            menu.setChildren(getChildren(menu, categoryEntities));
            return menu;
        })).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return entities;
    }

    @Override
    public CategoryEntity getCategoryPathById(Long catId) {
        CategoryEntity entity = this.getById(catId);
        if (entity == null) {
            return null;
        }
        CategoryEntity categoryEntity = entity;
        ArrayList<Long> path = new ArrayList<>();
        while (entity != null && entity.getCatId() != 0) {
            path.add(entity.getCatId());
            entity = this.getById(entity.getParentCid());
        }
        Collections.reverse(path);

        // 这里不能写((Long[]) (new ArrayList<>(path).toArray()))
        // 会有这种错误[Ljava.lang.Object; cannot be cast to [Ljava.lang.Long
        categoryEntity.setCatelogPath(new ArrayList<>(path).toArray(new Long[path.size()]));
        return categoryEntity;
    }

    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> allCategoryEntities) {

        return allCategoryEntities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, allCategoryEntities));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
    }

}