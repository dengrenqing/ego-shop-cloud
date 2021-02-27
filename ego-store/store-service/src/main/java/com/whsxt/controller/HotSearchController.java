package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.HotSearch;
import com.whsxt.domain.HotSearch;
import com.whsxt.service.HotSearchService;
import com.whsxt.service.HotSearchService;
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
@RequestMapping("/admin/hotSearch")
@Api(tags = "热搜管理")
public class HotSearchController {

    @Autowired
    private HotSearchService hotSearchService;

    @GetMapping("page")
    @ApiOperation("分页查询热搜")
    @PreAuthorize("hasAuthority('admin:hotSearch:page')")
    public ResponseEntity<IPage<HotSearch>> hotSearchPage(Page<HotSearch> page, HotSearch hotSearch) {
        IPage<HotSearch> hotSearchIPage = hotSearchService.findHotSearchPage(page, hotSearch);
        return ResponseEntity.ok(hotSearchIPage);
    }

    @PostMapping
    @ApiOperation("新增热搜")
    @PreAuthorize("hasAuthority('admin:hotSearch:save')")
    public ResponseEntity<Void> addHotSearch(@RequestBody HotSearch hotSearch) {
        hotSearchService.save(hotSearch);
        return ResponseEntity.ok().build();
    }


}
