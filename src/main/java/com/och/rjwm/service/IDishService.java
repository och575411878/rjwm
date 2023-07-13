package com.och.rjwm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.och.rjwm.entity.Dish;
import com.och.rjwm.entity.DishDto;

public interface IDishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
