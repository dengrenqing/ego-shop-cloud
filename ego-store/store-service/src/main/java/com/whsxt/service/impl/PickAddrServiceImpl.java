package com.whsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.PickAddrMapper;
import com.whsxt.domain.PickAddr;
import com.whsxt.service.PickAddrService;
import org.springframework.util.StringUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class PickAddrServiceImpl extends ServiceImpl<PickAddrMapper, PickAddr> implements PickAddrService {

    @Autowired
    private PickAddrMapper pickAddrMapper;


    /**
     * 分页查询自提点
     *
     * @param page
     * @param pickAddr
     * @return
     */
    @Override
    public IPage<PickAddr> findPickAddrPage(Page<PickAddr> page, PickAddr pickAddr) {
        return pickAddrMapper.selectPage(page, new LambdaQueryWrapper<PickAddr>()
                .like(StringUtils.hasText(pickAddr.getAddrName()), PickAddr::getAddrName, pickAddr.getAddrName())
        );
    }
}
