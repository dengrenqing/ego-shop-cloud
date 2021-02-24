package com.whsxt.service.impl;

import com.alibaba.fastjson.JSON;
import com.whsxt.constant.MenuConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.domain.SysMenu;
import com.whsxt.mapper.SysMenuMapper;
import com.whsxt.service.SysMenuService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加载菜单
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> loadUserMenu(String userId) {
        log.info("加载树菜单");
        // 从缓存里面拿
        String menuStr = redisTemplate.opsForValue().get(MenuConstant.MENU_PREFIX + userId);
        List<SysMenu> sysMenus = null;
        if (StringUtils.isEmpty(menuStr)) {
            // 查询数据库
            sysMenus = sysMenuMapper.findMenuByUserId(Long.valueOf(userId));
            if (CollectionUtils.isEmpty(sysMenus)) {
                // 说明该用户没有任何菜单
                return Collections.emptyList();
            }
            // 转成json串 存入redis
            redisTemplate.opsForValue().set(MenuConstant.MENU_PREFIX + userId, JSON.toJSONString(sysMenus), Duration.ofDays(7));
        } else {
            sysMenus = JSON.parseArray(menuStr, SysMenu.class);
        }
        // 循环组装树菜单
        return loadMenuTree(sysMenus, 0L);
    }

    /**
     * 组装树菜单
     * 自己思考递归怎么写
     *
     * @param sysMenus
     * @param pid
     * @return
     */
    private List<SysMenu> loadMenuTree(List<SysMenu> sysMenus, Long pid) {
        // 1.拿到顶级菜单
        List<SysMenu> root = sysMenus.stream()
                .filter(sysMenu -> sysMenu.getParentId().equals(pid))
                .collect(Collectors.toList());
        // 循环root
        root.stream().forEach(r -> {
            // 是的父id等于他的id
            List<SysMenu> child = sysMenus.stream()
                    .filter(sysMenu -> sysMenu.getParentId().equals(r.getMenuId()))
                    .collect(Collectors.toList());
            r.setList(child);
        });
        return root;
    }
}
