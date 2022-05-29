package reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reggie.common.R;
import reggie.dto.DishDto;
import reggie.entity.Category;
import reggie.entity.Dish;
import reggie.entity.DishFlavor;
import reggie.service.CategoryService;
import reggie.service.DishFlavorService;
import reggie.service.DishService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 23:56
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;


    //新增菜品
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    //分页查询 因为dish无category的name，所以需要用Page<DishDto> dishDtoPage
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //构造分页构造器
        Page<Dish> dishPage = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //构造条件构造器
        LambdaQueryWrapper<Dish> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件 按照name模糊查询
        LambdaQueryWrapper.like(name!= null, Dish::getName,name);
        //根据更新时间降序查询
        LambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        //启用查询方法
        dishService.page(dishPage, LambdaQueryWrapper);

        //设置新类型的集合，将其设置在新dishDtoPage的records中
        List<DishDto> dishDtoList = null;
        //对象拷贝 拷贝除了records的所有数据
        BeanUtils.copyProperties(dishPage, dishDtoPage,"records");
        //得到原版records
        List<Dish> records = dishPage.getRecords();

        //核心操作
       dishDtoList = records.stream().map((item)->{
            //拷贝dish有的内容
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            //每个item就是records集合的单个数据,得到单个数据的categoryId
            Long categoryId = item.getCategoryId();
            //得到查询到的实体对象
            Category category = categoryService.getById(categoryId);
            //得到需要的categoryname
            String categoryname = category.getName();
            //给dishDto设置categoryname
            dishDto.setCategoryName(categoryname);
            //返回disDto对象
            return dishDto;
        }).collect(Collectors.toList());//将每个对象收集成集合

        //将新集合数据设置给新records
        dishDtoPage.setRecords(dishDtoList);

        return R.success(dishDtoPage);
    }


    //修改菜品中,根据id返回菜品的数据（返回dto类型的）查询菜品，口味信息
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    //修改菜品
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    //删除菜品 接受多参数要加@RequestParam
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dishService.removeWithFlavor(ids);
        return R.success("菜品删除成功");
    }

    //添加套餐中添加菜品的数据回显功能，参数传为catetoryId，dish中有用dish复用性更强
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //构造查询条件
        LambdaQueryWrapper<Dish> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.eq(dish.getCategoryId()!=null, Dish::getCategoryId,dish.getCategoryId());
        //只查在售的
        QueryWrapper.eq(Dish::getStatus,1);
        //添加排序条件
        QueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        //进行查询，返回Dishlist数据
        List<Dish> list = dishService.list(QueryWrapper);
        //给dto增加口味数据
       List<DishDto> dishDtoList = list.stream().map((item)->{
            DishDto dishDto = new DishDto();
            //对象拷贝
            BeanUtils.copyProperties(item, dishDto);
            //查询菜品对应的口味，并得到
            Long id = dish.getId();
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
            List<DishFlavor> dishFlavors = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());


        return R.success(dishDtoList);
    }


}
