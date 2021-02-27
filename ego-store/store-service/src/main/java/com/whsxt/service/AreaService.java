package com.whsxt.service;

import com.whsxt.domain.Area;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
public interface AreaService extends IService<Area> {


    /**
     * 根据父id查询地址集合
     *
     * @param pid
     * @return
     */
    List<Area> findAreaByPid(Long pid);
}
