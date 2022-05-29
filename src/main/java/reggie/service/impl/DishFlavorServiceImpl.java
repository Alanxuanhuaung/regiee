package reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.entity.DishFlavor;
import reggie.mapper.DishFlavorMapper;
import reggie.service.DishFlavorService;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 23:37
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
