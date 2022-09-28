package com.iyyxx.springboot4ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyyxx.springboot4ruiji.common.CustomException;
import com.iyyxx.springboot4ruiji.entity.Category;
import com.iyyxx.springboot4ruiji.entity.Dish;
import com.iyyxx.springboot4ruiji.entity.Setmeal;
import com.iyyxx.springboot4ruiji.mapper.CategoryMapper;
import com.iyyxx.springboot4ruiji.service.CategoryService;
import com.iyyxx.springboot4ruiji.service.DishService;
import com.iyyxx.springboot4ruiji.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: CategoryServiceImpl
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 17:22:23
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        //添加查询条件，根据分类id进行查询菜品数据
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        // 如果已经关联，抛出一个业务异常
        if (count1 > 0) {
            throw new CustomException("当前分类下关联了菜品，不能删除");
            //已经关联菜品，抛出 一个业务异常
        }
        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            throw new CustomException("当前分类下关联了套餐，不能删除");
            //已经关联套餐，抛出 一个业务异常
        }
        //正常删除分类
        super.removeById(id);
    }
}
