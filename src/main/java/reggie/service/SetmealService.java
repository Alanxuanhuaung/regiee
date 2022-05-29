package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import reggie.dto.DishDto;
import reggie.dto.SetmealDto;
import reggie.entity.Setmeal;

import java.util.List;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 17:26
 */

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
    public void removeWithDish(List<Long> ids);
    public SetmealDto getByIdWithDish(Long id);
    public void updateWithDish(SetmealDto setmealDto);
}
