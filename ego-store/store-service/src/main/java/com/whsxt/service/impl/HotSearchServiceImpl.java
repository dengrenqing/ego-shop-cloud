package com.whsxt.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.domain.HotSearch;
import com.whsxt.mapper.HotSearchMapper;
import com.whsxt.service.HotSearchService;
import org.springframework.util.StringUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class HotSearchServiceImpl extends ServiceImpl<HotSearchMapper, HotSearch> implements HotSearchService {

    @Autowired
    private HotSearchMapper hotSearchMapper;

    /**
     * 分页查询热搜
     *
     * @param page
     * @param hotSearch
     * @return
     */
    @Override
    public IPage<HotSearch> findHotSearchPage(Page<HotSearch> page, HotSearch hotSearch) {
        return hotSearchMapper.selectPage(page, new LambdaQueryWrapper<HotSearch>()
                .eq(hotSearch.getStatus() != null, HotSearch::getStatus, hotSearch.getStatus())
                .like(StringUtils.hasText(hotSearch.getTitle()), HotSearch::getTitle, hotSearch.getTitle())
                .like(StringUtils.hasText(hotSearch.getTitle()), HotSearch::getContent, hotSearch.getContent())
        );
    }

    @Override
    public boolean save(HotSearch hotSearch) {
        log.info("新增热搜{}", JSON.toJSONString(hotSearch));
        hotSearch.setShopId(1L);
        hotSearch.setRecDate(new Date());
        return super.save(hotSearch);
    }
}
