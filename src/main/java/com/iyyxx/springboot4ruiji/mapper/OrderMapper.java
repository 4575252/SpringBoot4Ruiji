package com.iyyxx.springboot4ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iyyxx.springboot4ruiji.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @className: EmployeeMapper
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:31:15
 **/
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
