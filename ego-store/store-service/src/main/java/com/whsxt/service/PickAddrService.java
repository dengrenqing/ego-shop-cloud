package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.PickAddr;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 武汉尚学堂
 */
public interface PickAddrService extends IService<PickAddr> {


    /**
     * 分页查询自提点
     *
     * @param page
     * @param pickAddr
     * @return
     */
    IPage<PickAddr> findPickAddrPage(Page<PickAddr> page, PickAddr pickAddr);
}
