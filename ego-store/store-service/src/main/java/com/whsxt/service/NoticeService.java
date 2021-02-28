package com.whsxt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whsxt.vo.NoticeVo;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
public interface NoticeService extends IService<Notice> {


    /**
     * 分页查询公告
     *
     * @param page
     * @param notice
     * @return
     */
    IPage<Notice> findNoticePage(Page<Notice> page, Notice notice);

    /**
     * 加载前台公告
     *
     * @return
     */
    List<NoticeVo> findNoticeVo();

}
