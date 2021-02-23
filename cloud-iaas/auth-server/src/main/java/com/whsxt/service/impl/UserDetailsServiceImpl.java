package com.whsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whsxt.constant.AuthConstant;
import com.whsxt.domain.SysUser;
import com.whsxt.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     * 登录方法
     * 前台用户和后台用户公用一套登录
     * 在header放一个标识
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取头信息
        String loginType = request.getHeader(AuthConstant.LOGIN_TYPE);
        if (StringUtils.isEmpty(loginType)) {
            return null;
        }
        // 选择
        switch (loginType) {
            case AuthConstant.SYS_USER:
                // 后台用户 就查后台的sysUser表
                SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
                );
                if (!ObjectUtils.isEmpty(sysUser)) {
                    // 查询权限
                    List<String> auths = sysUserMapper.findUserAuthsById(sysUser.getUserId());
                    if (!CollectionUtils.isEmpty(auths)) {
                        // 设置权限
                        sysUser.setAuths(auths);
                    }
                }
                return sysUser;
            case AuthConstant.MEMBER:
                // 查前台的表
                return null;
            default:
                return null;
        }
    }
}
