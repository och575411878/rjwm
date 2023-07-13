package com.och.rjwm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.och.rjwm.common.PageResult;
import com.och.rjwm.common.R;
import com.och.rjwm.entity.Category;
import com.och.rjwm.entity.Dish;
import com.och.rjwm.entity.DishDto;
import com.och.rjwm.service.ICategoryService;
import com.och.rjwm.service.IDIshFlavorService;
import com.och.rjwm.service.IDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private IDishService dishService;
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IDIshFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 查询菜品及菜品口味信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    @GetMapping("/page")
    public R<PageResult<DishDto>> getPage(int page, int pageSize) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        dishService.page(pageInfo, lqw);
        PageResult<DishDto> rPageResult = new PageResult<>();
/*        rPageResult.setRecords(pageInfo.getRecords());
        rPageResult.setTotal(pageInfo.getTotal());*/
        List<DishDto> dishDtoList = pageInfo.getRecords().stream().map(dish -> {
                    DishDto dishDto = new DishDto();
                    BeanUtils.copyProperties(dish, dishDto);
                    Category category = categoryService.getById(dish.getCategoryId());
                    if (category != null) {
                        dishDto.setCategoryName(category.getName());
                    }
                    return dishDto;
                }
        ).collect(Collectors.toList());
        rPageResult.setRecords(dishDtoList);
        rPageResult.setTotal(pageInfo.getTotal());
        return R.success(rPageResult);
    }

    /**
     * 根据条件查询对应的菜品数据
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus, 1);
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }

}
