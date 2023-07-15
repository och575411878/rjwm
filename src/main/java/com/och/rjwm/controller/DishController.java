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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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
    private RedisTemplate redisTemplate;

    @Autowired
    private IDIshFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        //清理所有菜品的缓存数据
        //清理某个分类下面的菜品缓存数据
        String key="dish_"+dishDto.getCategoryId() +"_1";
        redisTemplate.delete(key);
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
        //清理所有菜品的缓存数据
        Set keys=redisTemplate.keys("dish_*"); //获取所有以dish_xxx开头的key
        redisTemplate.delete(keys); //删除这些key
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
    public R<List<DishDto>> list(Dish dish) {
        List<DishDto> dishDtoList = null;
        //动态构造key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();//dish_1397844391040167938_1
        //先从redis中获取缓存数据
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if(dishDtoList != null){
            //如果存在，直接返回，无需查询数据库
            return R.success(dishDtoList);
        }


        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus, 1);
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        dishDtoList= list.stream().map(dish1 -> {
            DishDto dish2 = new DishDto();
            BeanUtils.copyProperties(dish1, dish2);
            return dish2;
        }).collect(Collectors.toList());
        //如果不存在，需要查询数据库，将查询到的菜品数据缓存到Redis,这里是集合可以直接设置嘛
        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);
        return R.success(dishDtoList);
    }

}
