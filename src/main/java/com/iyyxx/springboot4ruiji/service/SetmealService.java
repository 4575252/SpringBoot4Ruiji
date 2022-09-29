package com.iyyxx.springboot4ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iyyxx.springboot4ruiji.dto.SetmealDto;
import com.iyyxx.springboot4ruiji.entity.Dish;
import com.iyyxx.springboot4ruiji.entity.Setmeal;

import java.util.List;

/**
 * @className: EmployeeService
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:32:45
 **/
public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    /***
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
