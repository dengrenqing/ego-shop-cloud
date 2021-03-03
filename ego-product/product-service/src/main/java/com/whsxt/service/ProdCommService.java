package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.ProdComm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whsxt.vo.ProdCommResult;

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

    /**
     * 前台查询商品的评论总览
     *
     * @param prodId
     * @return
     */
    ProdCommResult findFrontProdComm(Long prodId);

    /**
     * 分页查询前台商品的评论总览
     *
     * @param page
     * @param prodId
     * @param evaluate
     * @return
     */
    Page<ProdComm> getFrontProdCommPage(Page<ProdComm> page, Long prodId, Integer evaluate);

}
