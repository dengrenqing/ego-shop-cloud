package com.whsxt.service;

import com.whsxt.domain.UserAddr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author 武汉尚学堂
 */
public interface UserAddrService extends IService<UserAddr> {


    /**
     * 全查询用户的收货地址
     *
     * @param openId
     * @return
     */
    List<UserAddr> findUserAddr(String openId);

    /**
     * 修改默认收货地址
     *
     * @param openId
     * @param id
     */
    void changeUserDefaultAddr(String openId, Long id);
}
