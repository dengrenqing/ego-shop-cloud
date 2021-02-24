package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Category;
import com.whsxt.service.CategoryService;
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
@Api(tags = "商品分类查询")
@RestController
@RequestMapping("prod/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("table")
    @ApiOperation("分页查询商品的分类")
    @PreAuthorize("hasAuthority('prod:category:page')")
    public ResponseEntity<List<Category>> loadCategoryList() {
        List<Category> categoryList = categoryService.findCategoryList();
        return ResponseEntity.ok(categoryList);
    }


    @GetMapping("listCategory")
    @ApiOperation("查询商品的分类所有父节点所有的1级节点")
    @PreAuthorize("hasAuthority('prod:category:page')")
    public ResponseEntity<List<Category>> listCategory() {
        List<Category> categoryList = categoryService.findRootCategory();
        return ResponseEntity.ok(categoryList);
    }


    @PostMapping
    @ApiOperation("新增商品的分类")
    @PreAuthorize("hasAuthority('prod:category:save')")
    public ResponseEntity<Void> addCategory(@RequestBody Category category) {
        categoryService.save(category);
        return ResponseEntity.ok().build();
    }


}
