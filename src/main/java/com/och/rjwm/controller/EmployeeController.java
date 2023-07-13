package com.och.rjwm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.och.rjwm.common.PageResult;
import com.och.rjwm.common.R;
import com.och.rjwm.entity.Employee;
import com.och.rjwm.service.EmployeeServiceImpl;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    // TODO: 这里为什么要说不推荐字段注入
    @Resource
    EmployeeServiceImpl employeeService;

    // 这个应该不是RestFUL风格,而死直接映射.
    // RequestBody 解析请求体中的内容封装成一个对象.
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // MD5密码加密工具.
        assert employee != null;
        String password_md5 = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        // 判断数据库是否有当前账户.
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee employee1 = employeeService.getOne(lqw);
        if(employee1 == null)return R.error("账户不存在");
        if(!employee1.getPassword().equals(password_md5)){
            return  R.error("密码错误");
        }
        if(employee1.getStatus() == 0)return R.error("账户被禁止登录");

        request.getSession().setAttribute("employee",employee1.getId());
        return R.success(employee1);
    }

    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param request
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        System.out.println(LocalDateTime.now());
/*        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(request.getSession().getAttribute("employee").toString());
        employee.setUpdateUser( request.getSession().getAttribute("employee").toString());*/

        // 设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<PageResult<Employee>> pageSelect(int page, int pageSize, String name){
        //TODO: 分页查询的方法逻辑
        PageResult<Employee> employeePageResult = employeeService.pageSelect(page, pageSize,name);
        return  R.success(employeePageResult);
    }


    /**
     * 根据ID修改员工信息
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> disableEmployee(HttpServletRequest request, @RequestBody Employee employee){
        LambdaUpdateWrapper<Employee> lqw = new LambdaUpdateWrapper<>();
        // 记得修改更新用户与更新时间
        employee.setUpdateUser(request.getSession().getAttribute("employee").toString());
        employee.setUpdateTime(LocalDateTime.now());
        lqw.eq(Employee::getId,employee.getId());
        employeeService.update(employee,lqw);
        return R.success("成功更新");
    }


    /**
     * 根据ID查询员工信息
     */
    @GetMapping("/{Id}")
    public R<Employee> selectByID(@PathVariable String Id){
        Employee employee = employeeService.getById(Id);
        if(employee ==null)return R.error("没有查询到对应信息");
        return R.success(employee);
    }
}
