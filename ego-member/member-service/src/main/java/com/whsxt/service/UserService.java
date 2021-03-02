package com.whsxt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whsxt.domain.User;

import java.util.Map;

/**
 * @Author 武汉尚学堂
 */
public interface UserService extends IService<User> {


    /**
     * 发注册验证码
     *
     * @param openId
     * @param sendMap
     */
    void sendMsg(String openId, Map<String, String> sendMap);

    /**
     * 保存手机号
     *
     * @param openId
     * @param sendMap
     */
    void savePhone(String openId, Map<String, String> sendMap);

}
