package com.whsxt.service;


/**
 * @Author 武汉尚学堂
 */
public interface ImportService {

    /**
     * 全量导入 发生在项目一起动
     */
    void importAll();

    /**
     * 增量导入 定时任务
     */
    void importUpdate();


    /**
     * 快速导入 用户下单减库存了 mq实现
     */
    void quickImport();


}
