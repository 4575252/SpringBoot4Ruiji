package com.iyyxx.springboot4ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyyxx.springboot4ruiji.common.R;
import com.iyyxx.springboot4ruiji.dto.DishDto;
import com.iyyxx.springboot4ruiji.dto.SetmealDto;
import com.iyyxx.springboot4ruiji.entity.Category;
import com.iyyxx.springboot4ruiji.entity.Dish;
import com.iyyxx.springboot4ruiji.entity.Setmeal;
import com.iyyxx.springboot4ruiji.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: DishController
 * @description: 菜品管理
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:34:41
 **/
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息：{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }


    /***
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name != null, Setmeal::getName, name);

        // 添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);
        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item, setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /***
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids:{}", ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }


    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
//    @GetMapping("/{id}")
//    public R<DishDto> get(@PathVariable Long id) {
//        DishDto dishDto = setmealService.getByIdWithFlavor(id);
//        return R.success(dishDto);
//    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
//    @PutMapping
//    public R<String> update(@RequestBody DishDto dishDto) {
//        log.info(dishDto.toString());
//        dishService.updateWithFlavor(dishDto);
//        return R.success("修改菜品成功");
//    }
}

