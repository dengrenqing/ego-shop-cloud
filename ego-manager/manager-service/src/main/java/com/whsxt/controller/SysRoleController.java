package com.whsxt.controller;

import com.whsxt.domain.SysRole;
import com.whsxt.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@Api(tags = "角色的接口")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {


    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("list")
    @ApiOperation(value = "查询所有角色")
    @PreAuthorize("hasAuthority('sys:role:page')")
    public ResponseEntity<List<SysRole>> loadAllRole() {
        List<SysRole> roleList = sysRoleService.list();
        return ResponseEntity.ok(roleList);
    }


}
