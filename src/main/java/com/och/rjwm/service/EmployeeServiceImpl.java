package com.och.rjwm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.och.rjwm.common.PageResult;
import com.och.rjwm.entity.Employee;
import com.och.rjwm.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;


    @Override
    public PageResult<Employee> pageSelect(int pp,int pageSize,String name) {
        PageResult<Employee> employeePageResult = new PageResult<>();
        List<Employee> employeeList = employeeMapper.selectPage((pp - 1) * pageSize, pageSize,name);
        employeePageResult.setRecords(employeeList);
        employeePageResult.setTotal(employeeMapper.selectTotal(name));
        return employeePageResult;
    }
}
