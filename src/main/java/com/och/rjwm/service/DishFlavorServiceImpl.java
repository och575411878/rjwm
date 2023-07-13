package com.och.rjwm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.och.rjwm.entity.DishFlavor;
import com.och.rjwm.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>implements IDIshFlavorService {
}
