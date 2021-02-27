package com.whsxt.constant;

/**
 * @Author 武汉尚学堂
 * 队列的常量
 */
public interface QueueConstant {

    /**
     * 修改库存的队列
     */
    String PROD_CHANGE_QUEUE = "prod.change.queue";

    /**
     * 修改库存的交换机
     */
    String PROD_CHANGE_EX = "prod.change.ex";

    /**
     * 修改库存的路由key
     */
    String PROD_CHANGE_KEY = "prod.change.key";


}
