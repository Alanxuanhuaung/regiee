package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.User;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 11:49
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
