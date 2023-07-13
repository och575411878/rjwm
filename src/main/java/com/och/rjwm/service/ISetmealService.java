package com.och.rjwm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.och.rjwm.entity.Setmeal;
import com.och.rjwm.entity.SetmealDto;

import java.util.List;

public interface ISetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     *
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
