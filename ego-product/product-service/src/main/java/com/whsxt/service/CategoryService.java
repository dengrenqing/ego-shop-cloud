package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
public interface CategoryService extends IService<Category> {


    /**
     * 全查询商品的分类
     *
     * @return
     */
    List<Category> findCategoryList();

    /**
     * 查询商品的分类所有父节点所有的1级节点
     *
     * @return
     */
    List<Category> findRootCategory();

}
