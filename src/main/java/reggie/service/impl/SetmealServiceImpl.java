package reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reggie.dto.SetmealDto;
import reggie.entity.Setmeal;
import reggie.entity.SetmealDish;
import reggie.mapper.SetmealMapper;
import reggie.service.SetmealDishService;
import reggie.service.SetmealService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 17:27
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存setmeal到表
        this.save(setmealDto);
        //保存setmealdishdaobiao
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }


    public void removeWithDish(List<Long> ids){
        //删除setmeal中数据
        this.removeByIds(ids);
        //删除setmeal_dish数据
        LambdaQueryWrapper<SetmealDish> QueryWrapper = new LambdaQueryWrapper<>();
       QueryWrapper.in(SetmealDish::getDishId,ids);
            setmealDishService.remove(QueryWrapper);
    }

    @Override
    public SetmealDto getByIdWithDish(Long id) {
        SetmealDto setmealDto = new SetmealDto();
        //查询setmeal，并得到其id
        Setmeal setmeal = this.getById(id);
        Long setmealId = setmeal.getId();
        //对象拷贝
        BeanUtils.copyProperties(setmeal, setmealDto);
        //根据套餐id查对应的菜品
        LambdaQueryWrapper<SetmealDish> QueryWrapper = new LambdaQueryWrapper<>();
        //设置查询条件
        QueryWrapper.eq(SetmealDish::getSetmealId,setmealId);
        //查询启用
        List<SetmealDish> dishes = setmealDishService.list(QueryWrapper);
        //给dto设置dishes属性
        setmealDto.setSetmealDishes(dishes);

        return setmealDto;
    }

    //更新dish和套餐数据
    @Override
    public void updateWithDish(SetmealDto setmealDto) {
        //更新基本数据
        this.updateById(setmealDto);
        //清除套餐对应的dish setmealdish的delete
        LambdaQueryWrapper<SetmealDish> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(QueryWrapper);
        //插入更新后的dish   setmealdish的insert
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //给每个dish设置setmealid
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }
}
