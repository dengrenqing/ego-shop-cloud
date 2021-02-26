package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.ProdTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 武汉尚学堂
 */
public interface ProdTagService extends IService<ProdTag> {


    /**
     * 商品标签的分页查询
     *
     * @param page
     * @param prodTag
     * @return
     */
    IPage<ProdTag> findProdTagPage(Page<ProdTag> page, ProdTag prodTag);
}
