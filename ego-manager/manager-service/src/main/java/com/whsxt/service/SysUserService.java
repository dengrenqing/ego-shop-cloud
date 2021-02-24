package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 武汉尚学堂
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 分页查询管理员列表
     *
     * @param page
     * @param sysUser
     * @return
     */
    IPage<SysUser> findSysUserByPage(Page<SysUser> page, SysUser sysUser);
}
