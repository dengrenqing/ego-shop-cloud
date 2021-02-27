package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.IndexImg;
import com.whsxt.service.IndexImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 武汉尚学堂
 */
@RestController
@RequestMapping("admin/indexImg")
@Api(tags = "轮播图管理接口")
public class IndexImgController {

    @Autowired
    private IndexImgService indexImgService;


    @GetMapping("page")
    @ApiOperation("分页查询轮播图")
    @PreAuthorize("hasAuthority('admin:indexImg:page')")
    public ResponseEntity<IPage<IndexImg>> indexImgPage(Page<IndexImg> page, IndexImg indexImg) {
        IPage<IndexImg> indexImgIPage = indexImgService.findIndexImgPage(page, indexImg);
        return ResponseEntity.ok(indexImgIPage);
    }


    @PostMapping
    @ApiOperation("新增轮播图")
    @PreAuthorize("hasAuthority('admin:indexImg:save')")
    public ResponseEntity<Void> addIndexImg(@RequestBody IndexImg indexImg) {
        indexImgService.save(indexImg);
        return ResponseEntity.ok().build();
    }


    @GetMapping("info/{id}")
    @ApiOperation("根据id查询一个轮播图")
    @PreAuthorize("hasAuthority('admin:indexImg:info')")
    public ResponseEntity<IndexImg> getIndexImgInfo(@PathVariable("id") Long id) {
        IndexImg indexImg = indexImgService.findIndexImgById(id);
        return ResponseEntity.ok(indexImg);
    }


}
