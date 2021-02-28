package com.whsxt.controller;

import com.whsxt.domain.User;
import com.whsxt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 武汉尚学堂
 */
@RestController
@Api(tags = "会员管理接口")
public class UserController {


    @Autowired
    private UserService userService;


    @PutMapping("p/user/setUserInfo")
    @ApiOperation("更新用户的信息")
    public ResponseEntity<Void> setUserInfo(User user) {
        // id
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        user.setUserId(openId);
        userService.updateById(user);
        return ResponseEntity.ok().build();
    }


}
