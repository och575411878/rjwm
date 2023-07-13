package com.och.rjwm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.och.rjwm.entity.SetmealDish;
import com.och.rjwm.mapper.SetmealDishMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService{
}