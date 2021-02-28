package com.whsxt.controller;

import com.whsxt.domain.Area;
import com.whsxt.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@RestController
@RequestMapping("admin/area")
@Api(tags = "地址管理")
public class AreaController {

    @Autowired
    private AreaService areaService;


    @GetMapping("list")
    @ApiOperation("加载所有地址")
    @PreAuthorize("hasAuthority('admin:area:list')")
    public ResponseEntity<List<Area>> loadAllArea() {
        List<Area> areas = areaService.list();
        return ResponseEntity.ok(areas);
    }

    @PostMapping
    @ApiOperation("新增地址")
    @PreAuthorize("hasAuthority('admin:area:save')")
    public ResponseEntity<Void> addArea(@RequestBody @Validated Area area) {
        areaService.save(area);
        return ResponseEntity.ok().build();
    }


    @GetMapping("listByPid")
    @ApiOperation("根据父id查询地址集合")
//    @PreAuthorize("hasAuthority('admin:area:info')")
    public ResponseEntity<List<Area>> listByPid(Long pid) {
        List<Area> areas = areaService.findAreaByPid(pid);
        return ResponseEntity.ok(areas);
    }


}