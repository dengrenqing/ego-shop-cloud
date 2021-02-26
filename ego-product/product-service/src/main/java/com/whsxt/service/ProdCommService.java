package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.ProdComm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 武汉尚学堂
 */
public interface ProdCommService extends IService<ProdComm> {


    /**
     * 分页查询商品的评论
     *
     * @param page
     * @param prodComm
     * @return
     */
    IPage<ProdComm> findProdCommPage(Page<ProdComm> page, ProdComm prodComm);

}
