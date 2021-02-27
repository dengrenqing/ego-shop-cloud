package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.PickAddr;
import com.whsxt.service.PickAddrService;
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
@RequestMapping("shop/pickAddr")
@Api(tags = "自提点管理")
public class PickAddrController {

    @Autowired
    private PickAddrService pickAddrService;


    @GetMapping("page")
    @ApiOperation("分页查询自提点")
    @PreAuthorize("hasAuthority('shop:pickAddr:page')")
    public ResponseEntity<IPage<PickAddr>> pickAddrPage(Page<PickAddr> page, PickAddr pickAddr) {
        IPage<PickAddr> pickAddrIPage = pickAddrService.findPickAddrPage(page, pickAddr);
        return ResponseEntity.ok(pickAddrIPage);
    }

    @PostMapping
    @ApiOperation("新增自提点")
    @PreAuthorize("hasAuthority('shop:pickAddr:save')")
    public ResponseEntity<Void> addPickAddr(@RequestBody PickAddr pickAddr) {
        pickAddrService.save(pickAddr);
        return ResponseEntity.ok().build();
    }


}
