package reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reggie.common.R;
import reggie.entity.User;
import reggie.service.UserService;
import reggie.util.SendEmailCode;
import reggie.util.ValidateCodeUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 11:48
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    //发送验证码功能
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取邮箱号
        String phone = user.getPhone();
        //生成验证码
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        //发送验证码，用邮箱号存储，好取出对比
        SendEmailCode.send(code,phone);
        session.setAttribute(phone,code);

        return R.success("验证码发送成功");
    }

    //移动端用户登录   包含user的phone和code
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        //从map中获取邮箱号和验证码
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        //从session中获得保存的验证码
        String codeInSession = session.getAttribute(phone).toString();
        //提交的验证码和session保存的验证码进行比对。
        if (codeInSession!= null && codeInSession.equals(code)){
            //成功则登陆完成
            //判断是否是新用户，如果是新用户则自动完成注册
            LambdaQueryWrapper<User> QueryWrapper = new LambdaQueryWrapper<>();
            QueryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(QueryWrapper);
            if(user==null){
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            //将登陆的用户id保存入session
            session.setAttribute("user", user.getId());
            return R.success(user);
        }

        return R.error("登陆失败");
    }
    
}
