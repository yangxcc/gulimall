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
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
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

    public List<CategoryEntity> listTreeWithLocalLock() {
        String categoryTreeForCache = stringRedisTemplate.opsForValue().get("category_tree");
        if (!StringUtils.hasLength(categoryTreeForCache)) {
            List<CategoryEntity> data = listTreeCategoryWithLocalLock();
            return data;
        }

        // 将categoryTree中的数据反序列化成string
        return JSON.parseObject(categoryTreeForCache, new TypeReference<List<CategoryEntity>>() {
        });
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
        return JSON.parseObject(categoryTree, new TypeReference<List<CategoryEntity>>() {
        });
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

    public List<CategoryEntity> listTreeCategoryWithLocalLock() {
        // 因为springboot中的所有组件在容器中都是单例的，所以可以这么写，因为即使有100个请求，他们使用的都是这一个实例对象
        synchronized (this) {
            // 1. 得到锁之后，我们需要判断一下缓存中是否存在
            String dataFromCache = stringRedisTemplate.opsForValue().get("category_tree");
            if (StringUtils.hasLength(dataFromCache)) {
                return JSON.parseObject(dataFromCache, new TypeReference<List<CategoryEntity>>() {
                });
            }
            // 业务逻辑：查出全部category记录
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

            // 2. 必须要将写入缓存也放入到同步块中
            if (entities.isEmpty()) {
                // 将空值也加入缓存解决缓存穿透，并设置随机过期时间解决缓存雪崩
                stringRedisTemplate.opsForValue().set("category_tree", "", new Random().nextInt(100), TimeUnit.SECONDS);
            } else {
                // 将数据转成json格式的字符串
                String str = JSON.toJSONString(entities);
                stringRedisTemplate.opsForValue().set("category_tree", str);
            }

            return entities;
        }
    }

    public List<CategoryEntity> listTreeCategoryWithDistributedLock() {
        while (true) {
            // 1. 抢占分布式锁，加上过期时间，避免由于程序崩溃等原因导致没有将锁删除掉造成的死锁问题
            // 而且这里需要注意抢占锁和设置过期时间要使用原子操作，而不能分开，分开就有可能出于外部原因导致锁抢占到了，但是没有设置过期时间
            String token = UUID.randomUUID().toString();  // 为了避免由于业务代码超市，导致请求误删锁
            Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("LOCK", token, 30, TimeUnit.SECONDS);
            if (lock) {
                // 加锁成功
                List<CategoryEntity> result;
                try {
                    result = listTree();
                } finally {
                    // 解锁
//                if (Objects.equals(stringRedisTemplate.opsForValue().get("LOCK"), token)) {
//                    stringRedisTemplate.delete("LOCK");
//                }
                    String scripts = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                    stringRedisTemplate.execute(new DefaultRedisScript<Long>(scripts, Long.class), Collections.singletonList("LOCK"), token);
                }
                return result;
            } else {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // return listTreeCategoryWithDistributedLock();  // 加锁失败，自旋，使用循环实现，递归实现消耗的内存比较多
            }
        }
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