package com.iyyxx.springboot4ruiji.dto;

import com.iyyxx.springboot4ruiji.entity.Setmeal;
import com.iyyxx.springboot4ruiji.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
