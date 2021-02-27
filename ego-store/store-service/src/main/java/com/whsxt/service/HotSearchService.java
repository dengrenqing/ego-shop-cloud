package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.HotSearch;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 武汉尚学堂
 */
public interface HotSearchService extends IService<HotSearch> {


    /**
     * 分页查询热搜
     *
     * @param page
     * @param hotSearch
     * @return
     */
    IPage<HotSearch> findHotSearchPage(Page<HotSearch> page, HotSearch hotSearch);
}
