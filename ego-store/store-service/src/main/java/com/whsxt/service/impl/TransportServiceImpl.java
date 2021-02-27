package com.whsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.mapper.TransportMapper;
import com.whsxt.domain.Transport;
import com.whsxt.service.TransportService;

/**
 * @Author 武汉尚学堂
 * 
 */
@Service
public class TransportServiceImpl extends ServiceImpl<TransportMapper, Transport> implements TransportService{

}
