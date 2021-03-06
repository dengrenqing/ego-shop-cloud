package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.ProdComm;
import com.whsxt.service.ProdCommService;
import com.whsxt.vo.ProdCommResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 武汉尚学堂
 */
@RestController
@RequestMapping("prod/prodComm")
@Api(tags = "商品的评论管理接口")
public class ProdCommController {

    @Autowired
    private ProdCommService prodCommService;

    @GetMapping("page")
    @ApiOperation("分页查询商品的评论")
    @PreAuthorize("hasAuthority('prod:prodComm:page')")
    public ResponseEntity<IPage<ProdComm>> prodCommPage(Page<ProdComm> page, ProdComm prodComm) {
        IPage<ProdComm> prodCommIPage = prodCommService.findProdCommPage(page, prodComm);
        return ResponseEntity.ok(prodCommIPage);
    }


    @GetMapping("prodComm/prodCommData")
    @ApiOperation("前台查询商品的评论总览")
    public ResponseEntity<ProdCommResult> getFrontProdComm(Long prodId) {
        ProdCommResult prodCommResult = prodCommService.findFrontProdComm(prodId);
        return ResponseEntity.ok(prodCommResult);
    }


    @GetMapping("prodComm/prodCommPageByProd")
    @ApiOperation("分页查询前台商品的评论总览")
    public ResponseEntity<Page<ProdComm>> getFrontProdCommPage(Page<ProdComm> page, Long prodId, Integer evaluate) {
        Page<ProdComm> prodCommPage = prodCommService.getFrontProdCommPage(page, prodId, evaluate);
        return ResponseEntity.ok(prodCommPage);
    }


}
