package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import reggie.dto.DishDto;
import reggie.entity.Dish;

import java.util.List;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 17:25
 */

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);
    public DishDto getByIdWithFlavor(Long id);
    public void updateWithFlavor(DishDto dishDto);
    public void removeWithFlavor(List<Long> ids);
}
