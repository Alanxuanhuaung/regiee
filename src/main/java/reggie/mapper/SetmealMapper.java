package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.dto.SetmealDto;
import reggie.entity.Setmeal;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 17:25
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
}
