package reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.entity.Employee;
import reggie.mapper.EmployeeMapper;
import reggie.service.EmployeeService;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-19 16:56
 */
@Service//给spring的service注解          集成mybatis-plus给的类，填mapper和实体类
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
