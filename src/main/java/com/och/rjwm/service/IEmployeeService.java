package com.och.rjwm.service;

import com.och.rjwm.common.PageResult;
import com.och.rjwm.entity.Employee;

import java.util.List;

public interface IEmployeeService {
    PageResult<Employee> pageSelect(int pp,int pageSize,String name);
}
