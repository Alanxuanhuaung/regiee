package reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reggie.common.R;
import reggie.dto.DishDto;
import reggie.entity.Category;
import reggie.service.CategoryService;

import java.util.List;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 15:08
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    //新增分类
    @PostMapping//已有全局处理器，处理数据重复异常
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    //分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){//pageSize,需要与数据库名以及传参一致
        //创建分页构造器
        Page<Category> categoryPage = new Page<>(page, pageSize);
        //创建条件构造器
        LambdaQueryWrapper<Category> QueryWrapper = new LambdaQueryWrapper<>();
        //设置排序条件
        QueryWrapper.orderByAsc(Category::getSort);
        //进行分页查询
        categoryService.page(categoryPage, QueryWrapper);
        return R.success(categoryPage);
    }
    //删除功能
    @DeleteMapping
    public R<String> deleteById(Long ids){
        categoryService.remove(ids);
        return R.success("删除分类成功");
    }

    //修改分类功能
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    //新增菜品功能：根据动态数据查找分类数据返回给页面
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> QueryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        QueryWrapper.eq(category.getType()!=null, Category::getType,category.getType());
        //添加排序条件
        QueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(QueryWrapper);
        return R.success(list);
    }


}
