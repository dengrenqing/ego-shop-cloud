package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Prod;
import com.whsxt.service.ProdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 武汉尚学堂
 */
@RestController
@RequestMapping("/prod/prod")
@Api(tags = "商品的管理接口")
public class ProdController {

    @Autowired
    private ProdService prodService;

    @GetMapping("page")
    @ApiOperation("分页查询商品")
    public ResponseEntity<IPage<Prod>> prodPage(Page<Prod> page, Prod prod) {
        IPage<Prod> prodIPage = prodService.findProdPage(page, prod);
        return ResponseEntity.ok(prodIPage);
    }

    @PostMapping
    @ApiOperation("新增商品")
    public ResponseEntity<Void> addProd(@RequestBody Prod prod) {
        prodService.save(prod);
        return ResponseEntity.ok().build();
    }


    /**
     * 提供调用根据id查询商品信息
     *
     * @param prodId
     * @return
     */
    @GetMapping("findProdById")
    @ApiOperation("新增商品")
    Prod findProdById(@RequestParam("prodId") Long prodId) {
        return prodService.getById(prodId);
    }


}

