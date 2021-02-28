package com.whsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.UserAddrOrderMapper;
import com.whsxt.domain.UserAddrOrder;
import com.whsxt.service.UserAddrOrderService;

/**
 * @Author 武汉尚学堂
 * 
 */
@Service
public class UserAddrOrderServiceImpl extends ServiceImpl<UserAddrOrderMapper, UserAddrOrder> implements UserAddrOrderService{

}
