package com.och.rjwm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.och.rjwm.entity.User;
import com.och.rjwm.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
