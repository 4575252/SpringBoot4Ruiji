package com.iyyxx.springboot4ruiji.dto;

import com.iyyxx.springboot4ruiji.entity.Dish;
import com.iyyxx.springboot4ruiji.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
