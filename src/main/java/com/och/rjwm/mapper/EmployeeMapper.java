package com.och.rjwm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.och.rjwm.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    List<Employee> selectPage(int offset, int pageSize,String name);
    Long selectTotal(String name);
}
