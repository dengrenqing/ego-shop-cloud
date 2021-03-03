package com.whsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.dao.ProdEsDao;
import com.whsxt.domain.Prod;
import com.whsxt.domain.User;
import com.whsxt.es.ProdEs;
import com.whsxt.feign.ProdCommMemberFeign;
import com.whsxt.mapper.ProdMapper;
import com.whsxt.vo.ProdCommResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private ProdEsDao prodEsDao;

    @Autowired
    private ProdCommMemberFeign prodCommMemberFeign;


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


    /**
     * 前台查询商品的评论总览
     *
     * @param prodId
     * @return
     */
    @Override
    public ProdCommResult findFrontProdComm(Long prodId) {
        ProdCommResult prodCommResult = new ProdCommResult();
        // 直接从es拿好评率
        Optional<ProdEs> optionalProdEs = prodEsDao.findById(prodId);
        ProdEs prodEs = optionalProdEs.get();
        // 好评率
        BigDecimal positiveRating = prodEs.getPositiveRating();
        // 好评数
        Long praiseNumber = prodEs.getPraiseNumber();
        // 总评数 总的数量太多了 占内存 jvm可能直接爆炸
//        List<ProdComm> prodComms = prodCommMapper.selectList(new LambdaQueryWrapper<ProdComm>()
//                .eq(ProdComm::getProdId, prodId)
//        );
        Integer totalCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
        );
        Integer secondCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(ProdComm::getEvaluate, 1)
        );

        Integer badCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(ProdComm::getEvaluate, 2)
        );

        Integer picCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .isNotNull(ProdComm::getPics)
        );

        prodCommResult.setNumber(totalCount);
        prodCommResult.setNegativeNumber(badCount);
        prodCommResult.setSecondaryNumber(secondCount);
        prodCommResult.setPicNumber(picCount);
        prodCommResult.setPositiveRating(positiveRating);
        prodCommResult.setPraiseNumber(praiseNumber);
        return prodCommResult;
    }

    /**
     * 分页查询前台商品的评论总览
     * 1.分页查询评论
     * 2.远程调用获取用户信息
     * 3.组装数据返回
     *
     * @param page
     * @param prodId
     * @param evaluate
     * @return
     */
    @Override
    public Page<ProdComm> getFrontProdCommPage(Page<ProdComm> page, Long prodId, Integer evaluate) {

        Page<ProdComm> prodCommPage = prodCommMapper.selectPage(page, new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(evaluate != -1, ProdComm::getEvaluate, evaluate)
                .orderByDesc(ProdComm::getRecTime)
        );
        List<ProdComm> prodCommList = prodCommPage.getRecords();
        if (CollectionUtils.isEmpty(prodCommList)) {
            return prodCommPage;
        }
        // 评论要携带用户信息 远程调用 获取用户信息
        List<String> userIds = prodCommList.stream()
                .map(ProdComm::getUserId)
                .collect(Collectors.toList());
        // 发远程代用了 userId 获取到一个用户对象
        List<User> userList = prodCommMemberFeign.findUserInfoByUserIds(userIds);
        if (!CollectionUtils.isEmpty(userList)) {
            // 就算没有用户信息回来 我们也正常返回，前台给一个默认值就可以
            prodCommList.forEach(prodComm -> {
                User user1 = userList.stream()
                        .filter(user -> user.getUserId().equals(prodComm.getUserId()))
                        .collect(Collectors.toList())
                        .get(0);
                prodComm.setNickName(user1.getNickName());
                prodComm.setPic(user1.getPic());
            });
        }
        return prodCommPage;
    }
}
