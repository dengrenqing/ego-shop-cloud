package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Prod;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * @Author 武汉尚学堂
 */
public interface ProdService extends IService<Prod> {


    /**
     * 分页查询商品
     *
     * @param page
     * @param prod
     * @return
     */
    IPage<Prod> findProdPage(Page<Prod> page, Prod prod);

    /**
     * 根据区间查询商品的总条数
     *
     * @param t1
     * @param t2
     * @return
     */
    Integer getTotalCount(Date t1, Date t2);

    /**
     * 分页查询需要导入的商品
     *
     * @param page
     * @param t1
     * @param t2
     * @return
     */
    Page<Prod> findProdByPageToEs(Page<Prod> page, Date t1, Date t2);

    /**
     * 前台根据id查询商品的信息（包括了sku）
     *
     * @param prodId
     * @return
     */
    Prod findProdAndSkuById(Long prodId);
}
