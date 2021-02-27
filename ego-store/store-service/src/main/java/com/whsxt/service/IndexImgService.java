package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.IndexImg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 武汉尚学堂
 */
public interface IndexImgService extends IService<IndexImg> {


    /**
     * 分页查询轮播图
     *
     * @param page
     * @param indexImg
     * @return
     */
    IPage<IndexImg> findIndexImgPage(Page<IndexImg> page, IndexImg indexImg);

    /**
     * 根据id查询一个轮播图
     *
     * @param id
     * @return
     */
    IndexImg findIndexImgById(Long id);
}
