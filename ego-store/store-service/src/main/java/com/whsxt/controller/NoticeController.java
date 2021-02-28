package com.whsxt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Notice;
import com.whsxt.domain.PickAddr;
import com.whsxt.service.NoticeService;
import com.whsxt.vo.NoticeVo;
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
@RestController
@RequestMapping("/shop/notice")
@Api(tags = "公告管理")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("page")
    @ApiOperation("分页查询公告")
    @PreAuthorize("hasAuthority('shop:notice:page')")
    public ResponseEntity<IPage<Notice>> noticePage(Page<Notice> page, Notice notice) {
        IPage<Notice> noticeIPage = noticeService.findNoticePage(page, notice);
        return ResponseEntity.ok(noticeIPage);
    }

    @PostMapping
    @ApiOperation("新增公告")
    @PreAuthorize("hasAuthority('shop:notice:save')")
    public ResponseEntity<Void> addNotice(@RequestBody Notice notice) {
        noticeService.save(notice);
        return ResponseEntity.ok().build();
    }

    // ---------------------------

    @GetMapping("topNoticeList")
    @ApiOperation("加载前台公告")
    public ResponseEntity<List<NoticeVo>> loadFrontNotice() {
        List<NoticeVo> noticeVos = noticeService.findNoticeVo();
        return ResponseEntity.ok(noticeVos);
    }


}
