package com.whsxt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.UserCollection;
import com.whsxt.es.ProdEs;
import com.whsxt.service.UserCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @Author 武汉尚学堂
 */
@RestController
@Api("会员的收藏")
public class UserCollectController {

    @Autowired
    private UserCollectionService userCollectionService;


    @GetMapping("p/user/collection/count")
    @ApiOperation("查询用户收藏的数量")
    public ResponseEntity<Integer> getCollectCount() {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        int count = userCollectionService.count(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, openId)
        );
        return ResponseEntity.ok(count);
    }

    @GetMapping("p/user/collection/prods")
    @ApiOperation("分页查询用户收藏的商品")
    public ResponseEntity<IPage<ProdEs>> findUserCollectProd(Page<UserCollection> page) {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        IPage<ProdEs> prodEsIPage = userCollectionService.findUserCollectProd(page, openId);
        return ResponseEntity.ok(prodEsIPage);
    }

    @GetMapping("/p/user/collection/isCollection")
    @ApiOperation("查询用户是否收藏该商品")
    public ResponseEntity<Boolean> findUserCollectProd(Long prodId) {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Boolean flag = userCollectionService.findUserIsCollect(prodId, openId);
        return ResponseEntity.ok(flag);
    }


    @PostMapping("/p/user/collection/addOrCancel")
    @ApiOperation("用户添加或者取消收藏该商品")
    public ResponseEntity<Void> addOrCancel(@RequestBody Long prodId) {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userCollectionService.addOrCancelCollect(prodId, openId);
        return ResponseEntity.ok().build();
    }

}
