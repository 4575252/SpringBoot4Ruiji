package com.iyyxx.springboot4ruiji.controller;

import com.iyyxx.springboot4ruiji.common.R;
import com.iyyxx.springboot4ruiji.entity.Orders;
import com.iyyxx.springboot4ruiji.service.OrderDetailService;
import com.iyyxx.springboot4ruiji.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: DishController
 * @description: 用户管理
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:34:41
 **/
@Slf4j
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}", orders);
//        OrderService
        return null;
    }
}

