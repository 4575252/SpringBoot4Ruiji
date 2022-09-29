package com.iyyxx.springboot4ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyyxx.springboot4ruiji.entity.Dish;
import com.iyyxx.springboot4ruiji.entity.DishFlavor;
import com.iyyxx.springboot4ruiji.mapper.DishFlavorMapper;
import com.iyyxx.springboot4ruiji.mapper.DishMapper;
import com.iyyxx.springboot4ruiji.service.DishFlavorService;
import com.iyyxx.springboot4ruiji.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @className: CategoryServiceImpl
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 17:22:23
 **/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
