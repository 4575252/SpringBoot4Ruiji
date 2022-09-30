package com.iyyxx.springboot4ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iyyxx.springboot4ruiji.entity.Orders;

/**
 * @className: EmployeeService
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:32:45
 **/
public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}
