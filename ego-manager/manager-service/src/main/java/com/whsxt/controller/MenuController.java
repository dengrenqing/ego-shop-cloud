package com.whsxt.controller;

import com.whsxt.anno.Log;
import com.whsxt.domain.SysMenu;
import com.whsxt.service.SysMenuService;
import com.whsxt.vo.MenuAndAuths;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@Api(tags = "后台菜单接口")
@RestController
@RequestMapping("sys/menu")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;


    /**
     * 加载树菜单和权限的接口
     *
     * @return
     */
    @GetMapping("nav")
    @ApiOperation(value = "加载用户菜单和权限的接口")
//    @PreAuthorize("hasAuthority('')")
    @Log(operation = "加载用户菜单和权限的接口")
    public ResponseEntity<MenuAndAuths> loadMenuAndAuths() {
        //  1.获取当前用户id
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        // 查询树菜单
        List<SysMenu> menus = sysMenuService.loadUserMenu(userId);
        // 获取权限
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        // 转换一个权限
        List<String> auths = new ArrayList<>();
        authorities.forEach(authoritie -> auths.add(authoritie.getAuthority()));
        // 创建一个返回值
        MenuAndAuths menuAndAuths = new MenuAndAuths();
        menuAndAuths.setMenuList(menus);
        menuAndAuths.setAuthorities(auths);
        return ResponseEntity.ok(menuAndAuths);
    }


}
