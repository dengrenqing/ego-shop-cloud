package com.whsxt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whsxt.dao.ProdEsDao;
import com.whsxt.domain.Prod;
import com.whsxt.es.ProdEs;
import com.whsxt.service.ImportService;
import com.whsxt.service.ProdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class ImportServiceImpl implements ImportService, CommandLineRunner {

    @Autowired
    private ProdEsDao prodEsDao;

    @Autowired
    private ProdService prodService;

    /**
     * 每次导入的条数
     */
    @Value("${esimport.size}")
    private Integer size;

    /**
     * 全量导入 发生在项目一起动
     * mysql的prod导入es
     */
    @Override
    public void importAll() {
        log.info("开始全量导入");
        // 计算分页
        // 1. 查询所有的count
        Integer totalCount = prodService.getTotalCount(null, null);
        if (totalCount == null || totalCount == 0) {
            log.info("根本就没有数据让我们导入");
            return;
        }
        // 2. 计算分页 totalCount / size
        long page = totalCount % size == 0 ? totalCount / size : ((totalCount / size) + 1);
        // 循环
        for (int i = 1; i <= page; i++) {
            // 分页查询导入
            importToEs(i, size, null, null);
        }
    }

    /**
     * 导入es的方法
     *
     * @param current
     * @param size
     * @param t1
     * @param t2
     */
    private void importToEs(int current, Integer size, Date t1, Date t2) {
        // 1.分页查询
        // 组装一个page  如果让他做分页 查询总条数 小小的优化
        Page<Prod> page = new Page<>(current, size, false);
        Page<Prod> prodPage = prodService.findProdByPageToEs(page, t1, t2);
        List<Prod> prodList = prodPage.getRecords();
        // new  一个空的集合 专门放导入es的对象
        ArrayList<ProdEs> prodEsList = new ArrayList<>(prodList.size() * 2);
        prodList.forEach(prod -> {
            ProdEs prodEs = new ProdEs();
            //  对象copy
            BeanUtil.copyProperties(prod, prodEs, true);

            // 放入new的空集合
            prodEsList.add(prodEs);
        });
        // 批量导入es
        prodEsDao.saveAll(prodEsList);
    }

    /**
     * 增量导入 定时任务
     */
    @Override
    public void importUpdate() {

    }

    /**
     * 快速导入 用户下单减库存了 mq实现
     */
    @Override
    public void quickImport() {

    }

    /**
     * 当ioc容器初始化完成 当springboot启动完成 就会执行下面的方法
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        importAll();
    }
}
