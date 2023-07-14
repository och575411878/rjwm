package com.och.rjwm.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.och.rjwm.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
