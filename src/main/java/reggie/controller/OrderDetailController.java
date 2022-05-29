package reggie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reggie.service.OrderDetailsService;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 23:57
 */
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailsService orderDetailsService;
}
