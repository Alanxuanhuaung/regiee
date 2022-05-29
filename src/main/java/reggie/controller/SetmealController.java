package reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reggie.common.R;
import reggie.dto.DishDto;
import reggie.dto.SetmealDto;
import reggie.entity.Category;
import reggie.entity.Setmeal;
import reggie.service.CategoryService;
import reggie.service.SetmealDishService;
import reggie.service.SetmealService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-24 21:41
 */
@Slf4j
@RestController
@RequestMapping("setmeal")
public class SetmealController {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;

    //新增套餐，用Dto接受数据
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
            setmealService.saveWithDish(setmealDto);
            return R.success("新增套餐成功");
    }

    //分页查询  要显示套餐分类名称
    @GetMapping("page")
    public R<Page> page(int page, int pageSize,String name){
        //分页构造器
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Setmeal> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.like(name != null, Setmeal::getName,name);
        //排序条件  根据更新时间降序
        QueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //执行查询
        setmealService.page(setmealPage, QueryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(setmealPage, setmealDtoPage,"records");
        //得到原始records
        List<Setmeal> setmeals = setmealPage.getRecords();
        //创建dto集合
        List<SetmealDto> setmealDtos;
        //取出item的id并用于查询分类的name，设置给dto对象
       setmealDtos = setmeals.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝，拷贝基础数据
            BeanUtils.copyProperties(item, setmealDto);
            //用item的id查name
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            //给dto设置name
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());
        //将加入套餐类名的集合设置进records
        setmealDtoPage.setRecords(setmealDtos);
        return R.success(setmealDtoPage);
    }

    //删除套餐 接受多参数要加@RequestParam
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("套餐删除成功");
    }

    //修改菜品中,根据id返回菜品的数据（返回dto类型的）查询菜品，口味信息
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return R.success(setmealDto);
    }

    //修改套餐
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return R.success("修改菜品成功");
    }

    //根据类别查套餐
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,setmeal.getCategoryId());
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> setmeals = setmealService.list(setmealLambdaQueryWrapper);
        return R.success(setmeals);
    }
}
