package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.ProdProp;
import com.whsxt.domain.ProdPropValue;
import com.whsxt.service.ProdPropService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@Api(tags = "商品的属性管理接口")
@RestController
@RequestMapping("prod/spec")
public class ProdPropController {

    @Autowired
    private ProdPropService prodPropService;


    @GetMapping("page")
    @ApiOperation("分页查询商品的属性和属性值")
    @PreAuthorize("hasAuthority('prod:spec:page')")
    public ResponseEntity<IPage<ProdProp>> prodPropPage(Page<ProdProp> page, ProdProp prodProp) {
        IPage<ProdProp> prodPropIPage = prodPropService.findProdPropPage(page, prodProp);
        return ResponseEntity.ok(prodPropIPage);
    }


    @PostMapping
    @ApiOperation("新增商品的属性和属性值")
    @PreAuthorize("hasAuthority('prod:spec:save')")
    public ResponseEntity<Void> addProdProp(@RequestBody ProdProp prodProp) {
        prodPropService.save(prodProp);
        return ResponseEntity.ok().build();
    }

    @GetMapping("list")
    @ApiOperation("查询商品的属性和属性值")
    @PreAuthorize("hasAuthority('prod:spec:page')")
    public ResponseEntity<List<ProdProp>> findAllProdProp() {
        List<ProdProp> list = prodPropService.list();
        return ResponseEntity.ok(list);
    }


    @GetMapping("listSpecValue/{id}")
    @ApiOperation("根据查询商品的属性id查询属性值集合")
    @PreAuthorize("hasAuthority('prod:spec:page')")
    public ResponseEntity<List<ProdPropValue>> findPropValuesByPropId(@PathVariable("id") Long id) {
        List<ProdPropValue> prodPropValues = prodPropService.findPropValuesByPropId(id);
        return ResponseEntity.ok(prodPropValues);
    }

}
