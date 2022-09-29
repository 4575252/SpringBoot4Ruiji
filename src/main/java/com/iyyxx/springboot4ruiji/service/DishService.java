package com.iyyxx.springboot4ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iyyxx.springboot4ruiji.dto.DishDto;
import com.iyyxx.springboot4ruiji.entity.Category;
import com.iyyxx.springboot4ruiji.entity.Dish;

/**
 * @className: EmployeeService
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:32:45
 **/
public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);
}
