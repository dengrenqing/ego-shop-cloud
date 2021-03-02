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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.UserCollectionMapper;
import com.whsxt.domain.UserCollection;
import com.whsxt.service.UserCollectionService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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

    /**
     * 查询用户是否收藏该商品
     *
     * @param prodId
     * @param openId
     * @return
     */
    @Override
    public Boolean findUserIsCollect(Long prodId, String openId) {
        Integer count = userCollectionMapper.selectCount(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, openId)
                .eq(UserCollection::getProdId, prodId)
        );
        return count > 0;
    }

    /**
     * 用户添加或者取消收藏该商品
     *
     * @param prodId
     * @param openId
     */
    @Override
    public void addOrCancelCollect(Long prodId, String openId) {
        // 查询这个商品之前有没有被这个用户收藏过
        UserCollection userCollection = userCollectionMapper.selectOne(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, openId)
                .eq(UserCollection::getProdId, prodId)
        );
        if (ObjectUtils.isEmpty(userCollection)) {
            // 没有收藏 就要收藏了
            UserCollection newCollect = new UserCollection();
            newCollect.setProdId(prodId);
            newCollect.setUserId(openId);
            newCollect.setCreateTime(new Date());
            // 操作数据库
            userCollectionMapper.insert(newCollect);
            return;
        }
        // 如果之前有 就要取消
        userCollectionMapper.deleteById(userCollection.getId());
    }
}
