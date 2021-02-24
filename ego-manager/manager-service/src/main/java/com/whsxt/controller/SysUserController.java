package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.anno.Log;
import com.whsxt.domain.SysUser;
import com.whsxt.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 武汉尚学堂
 */
@Api(tags = "后台用户的接口")
@RestController
@RequestMapping("sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("info")
    @ApiOperation(value = "查询当前登录用户的信息")
    @Log(operation = "查询当前登录用户的信息")
    public ResponseEntity<SysUser> getUserInfo() {
        // 拿到id查询
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        SysUser sysUser = sysUserService.getById(Long.valueOf(userId));
        return ResponseEntity.ok(sysUser);
    }

    @GetMapping("page")
    @ApiOperation(value = "分页查询管理员列表")
    @PreAuthorize("hasAuthority('sys:user:page')")
    @Log(operation = "分页查询管理员列表")
    public ResponseEntity<IPage<SysUser>> getSysUserPage(Page<SysUser> page, SysUser sysUser) {
        IPage<SysUser> sysUserIPage = sysUserService.findSysUserByPage(page, sysUser);
        return ResponseEntity.ok(sysUserIPage);
    }

    @PostMapping
    @ApiOperation("新增用户")
    public ResponseEntity<Void> saveSysUser(@RequestBody SysUser sysUser) {
        // 拿到当前用户的id
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        sysUser.setCreateUserId(Long.valueOf(userId));
        sysUserService.save(sysUser);
        return ResponseEntity.ok().build();
    }
}