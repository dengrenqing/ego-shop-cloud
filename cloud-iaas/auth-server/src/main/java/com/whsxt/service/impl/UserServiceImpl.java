package com.whsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.UserMapper;
import com.whsxt.domain.User;
import com.whsxt.service.impl.UserService;
/**
 * @Author 武汉尚学堂
 * 
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
