package com.whsxt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whsxt.domain.UserAddr;
import com.whsxt.service.UserAddrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@RestController
@Api("用户收货地址管理")
public class UserAddrController {


    @Autowired
    private UserAddrService userAddrService;


    @GetMapping("p/address/list")
    @ApiOperation("全查询用户的收货地址")
    public ResponseEntity<List<UserAddr>> loadAllUserAddr() {
        // 当前用户
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        List<UserAddr> userAddrs = userAddrService.findUserAddr(openId);
        return ResponseEntity.ok(userAddrs);
    }


    @PostMapping("p/address/addAddr")
    @ApiOperation("新增收货地址")
    public ResponseEntity<Void> addUserAddr(@RequestBody UserAddr userAddr) {
        // 当前用户
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userAddr.setUserId(openId);
        userAddrService.save(userAddr);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/p/address/defaultAddr/{id}")
    @ApiOperation("修改默认收货地址")
    public ResponseEntity<Void> changeDefaultAddr(@PathVariable("id") Long id) {
        // 当前用户
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userAddrService.changeUserDefaultAddr(openId, id);
        return ResponseEntity.ok().build();
    }


    /**
     * 查询用户的默认收货地址
     *
     * @param openId
     * @return
     */
    @GetMapping("p/address/getDefaultAddr")
    @ApiOperation("查询用户的默认收货地址")
    UserAddr getDefaultAddr(@RequestParam("openId") String openId) {
        return userAddrService.getOne(new LambdaQueryWrapper<UserAddr>()
                .eq(UserAddr::getUserId, openId)
                .eq(UserAddr::getCommonAddr, 1)
        );
    }

}
