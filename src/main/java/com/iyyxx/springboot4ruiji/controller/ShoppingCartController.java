package com.iyyxx.springboot4ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iyyxx.springboot4ruiji.common.BaseContext;
import com.iyyxx.springboot4ruiji.common.R;
import com.iyyxx.springboot4ruiji.entity.ShoppingCart;
import com.iyyxx.springboot4ruiji.entity.User;
import com.iyyxx.springboot4ruiji.service.ShoppingCartService;
import com.iyyxx.springboot4ruiji.service.UserService;
import com.iyyxx.springboot4ruiji.utils.SMSUtils;
import com.iyyxx.springboot4ruiji.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @className: DishController
 * @description: 用户管理
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:34:41
 **/
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("shoppingCart:{}", shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        if (shoppingCart.getDishId() != null) {
            wrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            wrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart shoppingCartDb = shoppingCartService.getOne(wrapper);
        if (shoppingCartDb == null) {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            shoppingCartDb = shoppingCart;
        } else {
            shoppingCartDb.setNumber(shoppingCartDb.getNumber() + 1);
            shoppingCartService.updateById(shoppingCartDb);
        }
        return R.success(shoppingCartDb);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(ShoppingCart shoppingCart) {
        shoppingCart.setUserId(BaseContext.getCurrentId());
        log.info("shoppingCart:{}", shoppingCart);

        //条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != shoppingCart.getUserId(), ShoppingCart::getUserId,
                shoppingCart.getUserId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        return R.success(shoppingCartService.list(queryWrapper));
    }

    @DeleteMapping("clean")
    public R clean() {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        shoppingCartService.remove(wrapper);

        return R.success("清空成功");
    }


}

