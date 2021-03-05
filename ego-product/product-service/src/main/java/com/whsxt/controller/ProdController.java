package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Prod;
import com.whsxt.domain.Sku;
import com.whsxt.service.ProdService;
import com.whsxt.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author 武汉尚学堂
 */
@RestController
@RequestMapping("/prod/prod")
@Api(tags = "商品的管理接口")
public class ProdController {

    @Autowired
    private ProdService prodService;

    @Autowired
    private SkuService skuService;

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

    // 前台的接口
    @GetMapping("prod/prodInfo")
    @ApiOperation("前台根据id查询商品的信息（包括了sku）")
    public ResponseEntity<Prod> frontFindProdById(Long prodId) {
        Prod prod = prodService.findProdAndSkuById(prodId);
        return ResponseEntity.ok(prod);
    }


    /**
     * 远程调用根据skuIds查询sku的集合
     *
     * @param skuIds
     * @return
     */
    @PostMapping("/getSkuByIds")
    @ApiOperation("根据skuIds查询sku的集合")
    List<Sku> getSkuByIds(@RequestBody List<Long> skuIds) {
        return skuService.listByIds(skuIds);
    }


    /**
     * 修改库存的方法
     *
     * @param stockMap
     */
    @PostMapping("changeStock")
    @ApiOperation("修改库存的方法")
    void changeStock(@RequestBody Map<String, Map<Long, Integer>> stockMap) {
        prodService.changeStock(stockMap);
    }


}

