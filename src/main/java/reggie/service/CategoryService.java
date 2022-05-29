package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import reggie.entity.Category;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 14:39
 */

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
