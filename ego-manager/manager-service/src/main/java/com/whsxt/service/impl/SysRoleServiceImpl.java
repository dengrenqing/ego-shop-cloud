package com.whsxt.service.impl;

import com.alibaba.fastjson.JSON;
import com.whsxt.constant.RoleConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.SysRoleMapper;
import com.whsxt.domain.SysRole;
import com.whsxt.service.SysRoleService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 查询角色的列表
     *
     * @return
     */
    @Override
    public List<SysRole> list() {
        log.info("全查询角色列表");
        String roleStr = redisTemplate.opsForValue().get(RoleConstant.ROLE_PREFIX);
        List<SysRole> sysRoles = null;
        if (StringUtils.isEmpty(roleStr)) {
            // 查询数据库
            sysRoles = sysRoleMapper.selectList(null);
            if (CollectionUtils.isEmpty(sysRoles)) {
                return Collections.emptyList();
            }
            redisTemplate.opsForValue().set(RoleConstant.ROLE_PREFIX, JSON.toJSONString(sysRoles), Duration.ofDays(7));
        } else {
            sysRoles = JSON.parseArray(roleStr, SysRole.class);
        }
        return sysRoles;
    }
}
