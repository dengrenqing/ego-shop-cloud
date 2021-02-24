package com.whsxt.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.SysUserRole;
import com.whsxt.mapper.SysUserRoleMapper;
import com.whsxt.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.SysUserMapper;
import com.whsxt.domain.SysUser;
import com.whsxt.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 分页查询管理员列表
     *
     * @param page
     * @param sysUser
     * @return
     */
    @Override
    public IPage<SysUser> findSysUserByPage(Page<SysUser> page, SysUser sysUser) {
        // 排序
        page.addOrder(OrderItem.desc("create_time"));
        return sysUserMapper.selectPage(page, new LambdaQueryWrapper<SysUser>()
                .like(StringUtils.hasText(sysUser.getUsername()), SysUser::getUsername, sysUser.getUsername())
                .eq(sysUser.getStatus() != null, SysUser::getStatus, sysUser.getStatus())
        );
    }

    /**
     * 新增用户
     * 把用户的增删改查写完
     *
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean save(SysUser sysUser) {
        log.info("新增用户，操作员id:{}，新增内容为:{}", sysUser.getCreateUserId(), JSON.toJSONString(sysUser));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encode);
        sysUser.setCreateTime(new Date());
        sysUser.setShopId(1L);
        // 新增
        int insert = sysUserMapper.insert(sysUser);
        if (insert > 0) {
            Long userId = sysUser.getUserId();
            // 操作中间表
            List<Long> roleIds = sysUser.getRoleIdList();
            ArrayList<SysUserRole> sysUserRoles = new ArrayList<>(roleIds.size() * 2);
            roleIds.forEach(roleId -> {
                // 不要在循环里面操作数据库
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(roleId);
                sysUserRole.setUserId(userId);
                sysUserRoles.add(sysUserRole);
//                sysUserRoleMapper.insert(sysUserRole);
            });
            // 操作数据库
            sysUserRoleService.saveBatch(sysUserRoles);
        }
        return insert > 0;
    }
}
