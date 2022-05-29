package reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reggie.common.CustomException;
import reggie.entity.Category;
import reggie.entity.Dish;
import reggie.entity.Setmeal;
import reggie.mapper.CategoryMapper;
import reggie.service.CategoryService;
import reggie.service.DishService;
import reggie.service.SetmealService;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 14:39
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    //根据id删除分类，删之前检查当前分类是否关联菜品或套餐
    //若关联则抛出异常
    @Override
    public void remove(Long id) {
        //检查是否关联菜品，关联则抛出异常
        LambdaQueryWrapper<Dish> Wrapper = new LambdaQueryWrapper<Dish>();
        //添加查询条件，Select * from Dish where CategoryId = id
        Wrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(Wrapper);
        if (count>0){
            //关联菜品，抛出异常
            throw new CustomException("当前分类下关联了菜品不能删除");
        }
        //检查是否关联套餐，关联则抛出异常
        LambdaQueryWrapper<Setmeal> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(QueryWrapper);
        if (count1>0){
            //关联套餐，抛出异常
            throw new CustomException("当前分类下关联了套餐不能删除");
        }
        //正常删除
        super.removeById(id);
    }
}
