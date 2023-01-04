package com.gulu.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gulu.Common.R;
import com.gulu.Entity.Employee;
import com.gulu.Service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")//接收请求
public class EmployeeController {
    @Autowired//实现自动化装配，用于构造函数
    private EmployeeService employeeService;
/*
员工登录
@param request
@param employee
@return
 */
    //编写登录方法
    @PostMapping("/login")//前端发送的是Post的请求，故用PostMapping
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){


//        1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2、根据页面提交的用户名username查询数据库、
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());//添加查询条件
        //queryWrapper条件构造器，可以将查询条件放进里面，他就会自动安装对应的条件进行查询数据
        Employee emp = employeeService.getOne(queryWrapper);

//        3、如果没有查询到则返回登录失败结果
        if (emp==null) return R.error("登录失败，请核对账号密码");

//        4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)){
            return R.error("登录失败，请核对账号密码");
        }

//        5、查看员工状态，如果已禁用状态，则返回员工已禁用结果
        if (emp.getStatus()==0){
            return R.error("该账号已禁用");
        }

//        6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出功能
     * @param request
     * @return
     */
    @PostMapping("/logout")
    //HttpServletRequest request对象代表客户端请求，http请求头中的所有信息都封装在这个对象中，
    //通这个对象提供的方法，可以获得客户端请求的所有信息
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */

//    @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody  Employee employee){
        log.info("新增员工，员工信息:{}",employee.toString());
        //设置初始密码123456，需要进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());//获取系统时间
        employee.setUpdateTime(LocalDateTime.now());
        //获取当前登录用户的id
        Long empId=(Long)request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);//操作人
        employee.setUpdateUser(empId);//更新人
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
}
