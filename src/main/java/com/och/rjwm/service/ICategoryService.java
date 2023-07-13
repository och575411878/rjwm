package com.och.rjwm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.och.rjwm.entity.Category;

public interface ICategoryService extends IService<Category> {
    public  void  removeByID(String ID);
}
