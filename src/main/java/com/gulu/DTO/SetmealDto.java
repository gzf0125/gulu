package com.gulu.DTO;


import com.gulu.Entity.Setmeal;
import com.gulu.Entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
