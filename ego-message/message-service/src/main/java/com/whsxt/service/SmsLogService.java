package com.whsxt.service;

import com.aliyuncs.CommonResponse;
import com.whsxt.domain.SmsLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whsxt.model.AliSmsModel;
import com.whsxt.model.WxMsgModel;

/**
 * @Author 武汉尚学堂
 */
public interface SmsLogService extends IService<SmsLog> {


    /**
     * 记录发短信的数据库
     *
     * @param aliSmsModel
     */
    void saveMsg(AliSmsModel aliSmsModel, CommonResponse commonResponse);

    /**
     * 记录发微信消息的数据库
     *
     * @param wxMsgModel
     */
    void saveWxMsg(WxMsgModel wxMsgModel);
}
