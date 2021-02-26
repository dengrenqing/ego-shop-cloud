package com.whsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.domain.Prod;
import com.whsxt.mapper.ProdMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.ProdCommMapper;
import com.whsxt.domain.ProdComm;
import com.whsxt.service.ProdCommService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService {

    @Autowired
    private ProdCommMapper prodCommMapper;

    @Autowired
    private ProdMapper prodMapper;


    /**
     * 分页查询商品的评论
     *
     * @param page
     * @param prodComm
     * @return
     */
    @Override
    public IPage<ProdComm> findProdCommPage(Page<ProdComm> page, ProdComm prodComm) {
        // 排序
        page.addOrder(OrderItem.desc("rec_time"));
        String prodName = prodComm.getProdName();
        List<Long> prodIds = null;
        if (!StringUtils.isEmpty(prodName)) {
            // 如果商品名称不等于空  那么就先根据商品名称查询商品表 得到商品ids
            List<Prod> prods = prodMapper.selectList(new LambdaQueryWrapper<Prod>()
                    .like(Prod::getProdName, prodName)
            );
            if (!CollectionUtils.isEmpty(prods)) {
                // 拿到商品的ids
                prodIds = prods.stream()
                        .map(Prod::getProdId)
                        .collect(Collectors.toList());
            }
        }
        // 分页查询评论
        Page<ProdComm> prodCommPage = prodCommMapper.selectPage(page, new LambdaQueryWrapper<ProdComm>()
                .eq(prodComm.getStatus() != null, ProdComm::getStatus, prodComm.getStatus())
                .in(!CollectionUtils.isEmpty(prodIds), ProdComm::getProdId, prodIds)
        );
        // 拿到评论了
        List<ProdComm> prodCommList = prodCommPage.getRecords();
        if (!CollectionUtils.isEmpty(prodCommList)) {
            // 组装商品的名称
            // 根据评论的集合中的商品ids 查询商品
            List<Long> newProdIds = prodCommList.stream()
                    .map(ProdComm::getProdId)
                    .collect(Collectors.toList());
            // 查询商品表
            List<Prod> prods = prodMapper.selectBatchIds(newProdIds);
            // 组装数据
            prodCommList.stream().forEach(pc -> {
                // 找到对应的商品
                Prod prod1 = prods.stream()
                        .filter(prod -> prod.getProdId().equals(pc.getProdId()))
                        .collect(Collectors.toList())
                        .get(0);
                pc.setProdName(prod1.getProdName());
            });
        }
        return prodCommPage;
    }
}
