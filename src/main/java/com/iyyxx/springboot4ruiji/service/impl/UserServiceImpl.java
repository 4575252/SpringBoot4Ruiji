package com.iyyxx.springboot4ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyyxx.springboot4ruiji.common.CustomException;
import com.iyyxx.springboot4ruiji.dto.SetmealDto;
import com.iyyxx.springboot4ruiji.entity.Setmeal;
import com.iyyxx.springboot4ruiji.entity.SetmealDish;
import com.iyyxx.springboot4ruiji.entity.User;
import com.iyyxx.springboot4ruiji.mapper.SetmealMapper;
import com.iyyxx.springboot4ruiji.mapper.UserMapper;
import com.iyyxx.springboot4ruiji.service.SetmealDishService;
import com.iyyxx.springboot4ruiji.service.SetmealService;
import com.iyyxx.springboot4ruiji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: CategoryServiceImpl
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 17:22:23
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
