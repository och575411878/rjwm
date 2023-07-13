package com.och.rjwm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.och.rjwm.entity.Category;
import com.och.rjwm.entity.Dish;
import com.och.rjwm.entity.Setmeal;
import com.och.rjwm.exception.CustomException;
import com.och.rjwm.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    DishServiceImpl dishService;

    @Autowired
    SetmealServiceImpl setmealService;


    @Override
    public void removeByID(String ID) {
        LambdaQueryWrapper<Dish> lqwDish = new LambdaQueryWrapper<>();
        lqwDish.eq(Dish::getCategoryId, ID);
        int count = dishService.count(lqwDish);
        if (count > 0) {
            throw new CustomException("当前分类下存在其它菜品");
        }
        LambdaQueryWrapper<Setmeal> lqwSetmeal = new LambdaQueryWrapper<>();
        lqwSetmeal.eq(Setmeal::getCategoryId, ID);
        int count1 = setmealService.count(lqwSetmeal);
        if (count1 > 0) {
            throw new CustomException("当前分类下存在其它菜品");
        }
        LambdaQueryWrapper<Category> lqwCateGory = new LambdaQueryWrapper<>();
        lqwCateGory.eq(Category::getId, ID);

        this.remove(lqwCateGory);
    }


}
