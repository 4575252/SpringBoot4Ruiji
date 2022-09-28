package com.iyyxx.springboot4ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyyxx.springboot4ruiji.common.R;
import com.iyyxx.springboot4ruiji.entity.Category;
import com.iyyxx.springboot4ruiji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @className: EmployeeController
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 15:34:41
 **/
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /***
     * 新增分类
     * @param category
     * @return */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Category category) {
        log.info("category：{}", category.toString());
        categoryService.save(category);
        return R.success("新增员工成功");
    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize);
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        //添加排序条件
        queryWrapper.orderByDesc(Category::getSort);
        //执行查询
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /***
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id) {
        log.info("删除分类，id为{}", id);
        categoryService.remove(id);
        return R.success("分类删除成功");
    }

    @PutMapping
    public R<Category> update(HttpServletRequest request, @RequestBody Category category) {
        log.info("category:{}", category.toString());
        categoryService.updateById(category);
        return R.success(category);
    }

}

