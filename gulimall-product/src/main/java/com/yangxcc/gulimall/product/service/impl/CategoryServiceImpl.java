package com.yangxcc.gulimall.product.service.impl;

        import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
        import com.baomidou.mybatisplus.core.metadata.IPage;
        import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
        import com.yangxcc.common.utils.PageUtils;
        import com.yangxcc.common.utils.Query;
        import com.yangxcc.gulimall.product.dao.CategoryDao;
        import com.yangxcc.gulimall.product.entity.CategoryEntity;
        import com.yangxcc.gulimall.product.service.CategoryService;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Map;
        import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

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
        // 查出全部category记录
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        // 获得一级目录
        List<CategoryEntity> entities = categoryEntities.stream().filter(
                categoryEntity -> categoryEntity.getParentCid() == 0
        ).map((menu -> {
            menu.setChildren(getChildren(menu, categoryEntities));
            return menu;
        })).sorted((menu1, menu2)->{
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return entities;
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