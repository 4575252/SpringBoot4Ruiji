package com.iyyxx.springboot4ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iyyxx.springboot4ruiji.common.BaseContext;
import com.iyyxx.springboot4ruiji.common.R;
import com.iyyxx.springboot4ruiji.entity.Orders;
import com.iyyxx.springboot4ruiji.entity.ShoppingCart;
import com.iyyxx.springboot4ruiji.service.OrderService;
import com.iyyxx.springboot4ruiji.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: DishController
 * @description: 用户管理
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:34:41
 **/
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}", orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

}

