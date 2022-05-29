package reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import reggie.common.R;
import reggie.entity.Employee;
import reggie.service.impl.EmployeeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-19 16:58
 */
@Slf4j
@RestController//给spring的controller注解
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    //登陆功能
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
//        1.将页面提交的password进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        2.根据页面提交的username查询数据库
        LambdaQueryWrapper<Employee> QueryWrapper = new LambdaQueryWrapper<Employee>();
        QueryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(QueryWrapper);
//        3.如果未查询到则返回未查询到该用户
        if (emp == null){
            return R.error("未找到该用户");
        }
//        4.密码对比，如果不一致则返回密码错误
        if (!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }
//        5.查看员工状态，如果为已禁用状态则返回该员工已禁用
        if (emp.getStatus() == 0){
            return R.error("该员工已禁用");
        }
//        6.登陆成功，将员工id存入Session并返回登陆成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }
    //退出功能
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //移除当前登陆员工session中保存的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    // 添加功能
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
     //设置初始密码并加密处理
     employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//     employee.setCreateTime(LocalDateTime.now());
//     employee.setUpdateTime(LocalDateTime.now());
     //获取创建人
        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功");
    }
     //按id查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper <Employee> QueryWrapper = new LambdaQueryWrapper();
        //添加过滤条件      导包com.aphce的             类的get方法用::写法 传的参
        QueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        QueryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo, QueryWrapper);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(@RequestBody Employee employee){
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateUser(empId);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }
            return R.error("该员工不存在");

    }
}
