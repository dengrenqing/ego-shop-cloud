package com.whsxt.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.constant.CategoryConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.domain.Category;
import com.whsxt.mapper.CategoryMapper;
import com.whsxt.service.CategoryService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "com.whsxt.service.impl.CategoryServiceImpl")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 全查询商品的分类
     * 需要缓存
     *
     * @return
     */
    @Override
    @Cacheable(key = CategoryConstant.CATEGORY_PREFIX)
    public List<Category> findCategoryList() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .orderByDesc(Category::getUpdateTime)
        );
    }

    /**
     * 查询商品的分类所有父节点所有的1级节点
     *
     * @return
     */
    @Override
    @Cacheable(key = CategoryConstant.CATEGORY_ROOT_PREFIX)
    public List<Category> findRootCategory() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId, 0L)
        );
    }

    /**
     * 新增商品分类
     * 删除所有相关缓存 不然出现脏读
     *
     * @param category
     * @return
     */
    @Override
    @Caching(evict = {
            @CacheEvict(key = CategoryConstant.CATEGORY_ROOT_PREFIX),
            @CacheEvict(key = CategoryConstant.CATEGORY_PREFIX)
    })
    public boolean save(Category category) {
        log.info("新增商品分类{}", JSON.toJSON(category));
        category.setRecTime(new Date());
        category.setUpdateTime(new Date());
        category.setShopId(1L);
        // 新增父节点 还是子节点
        Long parentId = category.getParentId();
        if (ObjectUtils.isEmpty(parentId)) {
            // 新增父节点
            parentId = 0L;
            category.setGrade(1);
        } else {
            // 新增子节点
            // 校验一下
//            Category parent = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
//                    .eq(Category::getParentId, parentId)
//            );
            Category parent = categoryMapper.selectById(parentId);
            if (ObjectUtils.isEmpty(parent)) {
                // 父节点不存在
                throw new IllegalArgumentException("新增商品分类时父节点不存在");
            }
            category.setGrade(2);
        }
        // 新增
        return categoryMapper.insert(category) > 0;
    }
}
