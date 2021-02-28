package com.whsxt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.constant.IndexImgConstant;
import com.whsxt.domain.Prod;
import com.whsxt.feign.IndexImgProdFeign;
import com.whsxt.vo.IndexImgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.IndexImgMapper;
import com.whsxt.domain.IndexImg;
import com.whsxt.service.IndexImgService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "com.whsxt.service.impl.IndexImgServiceImpl")
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Autowired
    private IndexImgProdFeign indexImgProdFeign;

    /**
     * 分页查询轮播图
     *
     * @param page
     * @param indexImg
     * @return
     */
    @Override
    public IPage<IndexImg> findIndexImgPage(Page<IndexImg> page, IndexImg indexImg) {
        // 排序
        page.addOrder(OrderItem.asc("seq"));
        return indexImgMapper.selectPage(page, new LambdaQueryWrapper<IndexImg>()
                .eq(!ObjectUtils.isEmpty(indexImg.getStatus()), IndexImg::getStatus, indexImg.getStatus())
        );
    }

    /**
     * 根据id查询一个轮播图
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(key = "#{id}")
    public IndexImg findIndexImgById(Long id) {
        // 查询轮播图的库
        IndexImg indexImg = indexImgMapper.selectById(id);
        if (ObjectUtils.isEmpty(indexImg)) {
            return null;
        }
        // 判断有没有商品关联
        Long prodId = indexImg.getRelation();
        if (!ObjectUtils.isEmpty(prodId)) {
            // 有商品关联 远程调用商品服务拿到商品对象
            Prod prod = indexImgProdFeign.findProdById(prodId);
            if (!ObjectUtils.isEmpty(prod)) {
                indexImg.setProdName(prod.getProdName());
                indexImg.setPic(prod.getPic());
            }
        }
        return indexImg;
    }

    /**
     * 因为轮播图要和商品关联
     * 新增
     *
     * @param indexImg
     * @return
     */
    @Override
    @CacheEvict(key = IndexImgConstant.INDEX_IMG_PREFIX)
    public boolean save(IndexImg indexImg) {
        Boolean status = indexImg.getStatus();
        if (status) {
            indexImg.setUploadTime(new Date());
        }
        indexImg.setShopId(1L);
        return super.save(indexImg);
    }


    /**
     * 加载前台轮播图接口
     * 可以做缓存的
     *
     * @return
     */
    @Override
    @Cacheable(key = IndexImgConstant.INDEX_IMG_PREFIX)
    public List<IndexImgVo> findFrontIndexImg() {
        // 1. 查询数据库
        List<IndexImg> indexImgs = indexImgMapper.selectList(new LambdaQueryWrapper<IndexImg>()
                .eq(IndexImg::getStatus, 1)
                .orderByAsc(IndexImg::getSeq)
        );
        if (CollectionUtils.isEmpty(indexImgs)) {
            return Collections.emptyList();
        }
        // 如果不等于空
        // 转换对象
        ArrayList<IndexImgVo> indexImgVos = new ArrayList<>(indexImgs.size() * 2);
        indexImgs.forEach(indexImg -> {
            IndexImgVo indexImgVo = new IndexImgVo();
            BeanUtil.copyProperties(indexImg, indexImgVo, true);
            indexImgVos.add(indexImgVo);
        });
        return indexImgVos;
    }
}
