package reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reggie.dto.DishDto;
import reggie.entity.Dish;
import reggie.entity.DishFlavor;
import reggie.entity.SetmealDish;
import reggie.mapper.DishMapper;
import reggie.service.DishFlavorService;
import reggie.service.DishService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 17:26
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存基本信息到菜品表
        this.save(dishDto);
        //得到菜品的id
        Long dishId = dishDto.getId();
        //保存菜品口味数据到dish_flavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        //保存菜品口味数据到dish_flavor
        dishFlavorService.saveBatch(flavors);

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        //从dish表查基本信息
        Dish dish = this.getById(id);
        //对象拷贝
        BeanUtils.copyProperties(dish, dishDto);
        //从口味表查口味信息,通过菜品的id来查对应的口味
        LambdaQueryWrapper<DishFlavor> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(QueryWrapper);
        //设置dishDto的flavor属性
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更改dish数据
        this.updateById(dishDto);
        //先清理当前菜品对应的口味数据  dish_flavor的delete操作
        LambdaQueryWrapper<DishFlavor> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(QueryWrapper);
        //添加当前菜品对应的口味数据    dish_flavor的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        //批量保存
        dishFlavorService.saveBatch(flavors);
    }

    //菜品删除
    public void removeWithFlavor(List<Long> ids){
        //删除dish中数据
        this.removeByIds(ids);
        //删除dish_flavor数据
        LambdaQueryWrapper<DishFlavor> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.in(DishFlavor::getDishId,ids);
        dishFlavorService.remove(QueryWrapper);
    }


}
