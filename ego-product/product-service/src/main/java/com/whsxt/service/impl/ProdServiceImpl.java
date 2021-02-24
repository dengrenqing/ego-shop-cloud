package com.whsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whsxt.domain.Prod;
import com.whsxt.mapper.ProdMapper;
import com.whsxt.service.ProdService;

/**
 * @Author 武汉尚学堂
 * 
 */
@Service
public class ProdServiceImpl extends ServiceImpl<ProdMapper, Prod> implements ProdService{

}
