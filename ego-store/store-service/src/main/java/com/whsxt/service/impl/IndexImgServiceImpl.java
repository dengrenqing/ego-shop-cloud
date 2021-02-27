package com.whsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Prod;
import com.whsxt.feign.IndexImgProdFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.IndexImgMapper;
import com.whsxt.domain.IndexImg;
import com.whsxt.service.IndexImgService;
import org.springframework.util.ObjectUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
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
    public boolean save(IndexImg indexImg) {
        Boolean status = indexImg.getStatus();
        if (status) {
            indexImg.setUploadTime(new Date());
        }
        indexImg.setShopId(1L);
        return super.save(indexImg);
    }
}
