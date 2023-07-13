package com.och.rjwm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.och.rjwm.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
