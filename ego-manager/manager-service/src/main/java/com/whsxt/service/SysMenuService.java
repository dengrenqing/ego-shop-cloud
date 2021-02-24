package com.whsxt.service;

import com.whsxt.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
public interface SysMenuService extends IService<SysMenu> {


    /**
     * 加载菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> loadUserMenu(String userId);
}
