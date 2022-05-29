package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.Employee;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-19 16:52
 */
@Mapper//                       继承mybatis-plus给的接口填实体类
public interface EmployeeMapper extends BaseMapper<Employee> {
}
