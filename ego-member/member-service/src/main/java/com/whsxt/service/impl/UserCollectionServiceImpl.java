package com.whsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.es.ProdEs;
import com.whsxt.feign.UserCollectProdFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.UserCollectionMapper;
import com.whsxt.domain.UserCollection;
import com.whsxt.service.UserCollectionService;
import org.springframework.util.CollectionUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {

    @Autowired
    private UserCollectionMapper userCollectionMapper;

    @Autowired
    private UserCollectProdFeign userCollectProdFeign;


    /**
     * 分页查询用户收藏的商品
     *
     * @param page
     * @param openId
     * @return
     */
    @Override
    public IPage<ProdEs> findUserCollectProd(Page<UserCollection> page, String openId) {
        // 分页查询收藏表
        Page<UserCollection> collectionPage = userCollectionMapper.selectPage(page, new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, openId)
        );

        // new 一个最终的分页对象
        Page<ProdEs> prodEsPage = new Page<>(page.getCurrent(), page.getSize());

        List<UserCollection> userCollections = collectionPage.getRecords();
        if (CollectionUtils.isEmpty(userCollections)) {
            return prodEsPage;
        }
        // 拿到收藏表的集合了 并没有商品信息
        List<Long> prodIds = userCollections.stream()
                .map(UserCollection::getProdId)
                .collect(Collectors.toList());
        // 查询es 远程调用 查询es
        List<ProdEs> prodEsList = userCollectProdFeign.findProdEsByIds(prodIds);

        prodEsPage.setTotal(collectionPage.getTotal());
        prodEsPage.setRecords(prodEsList);
        return prodEsPage;
    }
}
