package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import reggie.entity.Orders;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 23:53
 */
public interface OrdersService extends IService<Orders> {
   //用户下单
    public void submit(Orders orders);
}
