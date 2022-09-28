package com.iyyxx.springboot4ruiji.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyyxx.springboot4ruiji.entity.Employee;
import com.iyyxx.springboot4ruiji.mapper.EmployeeMapper;
import com.iyyxx.springboot4ruiji.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @className: EmployeeServiceImpl
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:33:31
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}
