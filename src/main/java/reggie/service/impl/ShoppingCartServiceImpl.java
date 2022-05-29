package reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.entity.ShoppingCart;
import reggie.mapper.ShoppingCartMapper;
import reggie.service.ShoppingCartService;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 22:33
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
