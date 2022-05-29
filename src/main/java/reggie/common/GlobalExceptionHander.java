package reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-22 16:01
 */
//拦截restcontroller注解
//@RestControllerAdvice = @ControllerAdvice(annotations = RestController.class) + @ResponseBody
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHander {
    //异常处理方法，处理sql类异常
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHander(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")){
            return R.error("用户名已存在");
        }
        return R.error("操作失败");
    }

    //异常处理方法，处理自定义类异常
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHander(CustomException ex){
        log.error(ex.getMessage());

        return R.error(ex.getMessage());
    }


}
