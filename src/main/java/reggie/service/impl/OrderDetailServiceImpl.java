package reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.entity.OrderDetail;
import reggie.mapper.OrderDetailMapper;
import reggie.service.OrderDetailsService;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 23:54
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper,OrderDetail> implements OrderDetailsService {
}
