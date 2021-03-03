package com.whsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.OrderSettlementMapper;
import com.whsxt.domain.OrderSettlement;
import com.whsxt.service.OrderSettlementService;

/**
 * @Author 武汉尚学堂
 * 
 */
@Service
public class OrderSettlementServiceImpl extends ServiceImpl<OrderSettlementMapper, OrderSettlement> implements OrderSettlementService{

}
