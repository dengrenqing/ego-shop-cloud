package com.whsxt.service.impl;

import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.whsxt.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.UserMapper2;
import com.whsxt.service.UserService;

import java.util.Date;

/**
 * @Author 武汉尚学堂
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper2, User> implements UserService {

    @Autowired
    private UserMapper2 userMapper2;


    @Override
    public boolean updateById(User entity) {
        entity.setModifyTime(new Date());
        entity.setUserLasttime(new Date());
        int i = userMapper2.updateById(entity);
        return i > 0;
    }
}
