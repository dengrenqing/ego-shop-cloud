package com.whsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.OrderItemMapper;
import com.whsxt.domain.OrderItem;
import com.whsxt.service.OrderItemService;

/**
 * @Author 武汉尚学堂
 * 
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService{

}
