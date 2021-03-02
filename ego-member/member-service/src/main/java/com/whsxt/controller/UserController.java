package com.whsxt.controller;

import com.alibaba.fastjson.JSON;
import com.whsxt.constant.QueueConstant;
import com.whsxt.domain.User;
import com.whsxt.model.WxMsgModel;
import com.whsxt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 武汉尚学堂
 */
@RestController
@Api(tags = "会员管理接口")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @PutMapping("p/user/setUserInfo")
    @ApiOperation("更新用户的信息")
    public ResponseEntity<Void> setUserInfo(User user) {
        // id
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        user.setUserId(openId);
        userService.updateById(user);
        return ResponseEntity.ok().build();
    }


    @PostMapping("p/sms/send")
    @ApiOperation("发注册验证码")
    public ResponseEntity<String> sendMsg(@RequestBody Map<String, String> sendMap) {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userService.sendMsg(openId, sendMap);
        return ResponseEntity.ok("发送成功，请稍后再短信中查看");
    }


    @PostMapping("p/sms/savePhone")
    @ApiOperation("保存手机号")
    public ResponseEntity<String> savePhone(@RequestBody Map<String, String> sendMap) {
        String openId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userService.savePhone(openId, sendMap);
        return ResponseEntity.ok("保存成功");
    }


    @PostMapping("p/sms/wxSend")
    @ApiOperation("测试发送微信公众号消息")
    public ResponseEntity<String> wxSend(String openId, String name) {
        // 构建一个对象
        WxMsgModel wxMsgModel = new WxMsgModel();
        wxMsgModel.setToUser(openId);
        wxMsgModel.setUrl("https://www.baidu.com");
        wxMsgModel.setTemplateId("ZYslZEhVwU0eio66aWznGld5_GuRn_gCcAt-STtMd-k");
        wxMsgModel.setTopColor("#FF0000");
        HashMap<String, Map<String, String>> data = new HashMap<>();
        data.put("userName", WxMsgModel.buildMap(name, "#FF0000"));
        data.put("time", WxMsgModel.buildMap(LocalDateTime.now().toString(), "#FF0000"));
        data.put("product", WxMsgModel.buildMap("女朋友", "#FF0000"));
        data.put("money", WxMsgModel.buildMap("0.1", "#FF0000"));
        wxMsgModel.setData(data);

        // 用mq发消息
        rabbitTemplate.convertAndSend(QueueConstant.WECHAT_SEND_EX, QueueConstant.WECHAT_SEND_KEY, JSON.toJSONString(wxMsgModel));

        return ResponseEntity.ok("发送成功");
    }


}
