package com.whsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.es.ProdEs;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
public interface SearchService {


    /**
     * 根据标签分组查询
     *
     * @param tagId
     * @param current
     * @param size
     * @return
     */
    Page<ProdEs> findProdByTagId(Long tagId, Integer current, Integer size);

    /**
     * 根据关键字查询
     *
     * @param prodName
     * @param current
     * @param size
     * @param sort
     * @return
     */
    Page<ProdEs> findProdByKeywords(String prodName, Integer current, Integer size, Integer sort);

    /**
     * 提供远程调用根据ids查询商品信息
     *
     * @param prodIds
     * @return
     */
    List<ProdEs> findProdByIds(List<Long> prodIds);
}