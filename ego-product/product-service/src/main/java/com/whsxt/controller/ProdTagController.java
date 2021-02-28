package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.ProdTag;
import com.whsxt.service.ProdTagService;
import com.whsxt.vo.ProdTagVo;
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
@Api(tags = "商品的标签管理接口")
@RestController
@RequestMapping("prod/prodTag")
public class ProdTagController {


    @Autowired
    private ProdTagService prodTagService;


    @GetMapping("page")
    @ApiOperation("商品标签的分页查询")
    @PreAuthorize("hasAuthority('prod:prodTag:page')")
    public ResponseEntity<IPage<ProdTag>> prodTagPage(Page<ProdTag> page, ProdTag prodTag) {
        IPage<ProdTag> prodTagIPage = prodTagService.findProdTagPage(page, prodTag);
        return ResponseEntity.ok(prodTagIPage);
    }


    @PostMapping
    @ApiOperation("新增商品的标签")
    @PreAuthorize("hasAuthority('prod:prodTag:save')")
    public ResponseEntity<Void> addProdTag(@RequestBody ProdTag prodTag) {
        prodTagService.save(prodTag);
        return ResponseEntity.ok().build();
    }


    @GetMapping("listTagList")
    @ApiOperation("全查询商品的标签分组")
    @PreAuthorize("hasAuthority('prod:prodTag:page')")
    public ResponseEntity<List<ProdTag>> findAllTag() {
        List<ProdTag> list = prodTagService.list();
        return ResponseEntity.ok(list);
    }
// -----------------------------


    @GetMapping("prodTagList")
    @ApiOperation("加载前台的标签分组")
    public ResponseEntity<List<ProdTagVo>> loadFrontProdTag() {
        List<ProdTagVo> prodTagVos = prodTagService.findProdTagVo();
        return ResponseEntity.ok(prodTagVos);
    }


}
