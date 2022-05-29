package reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.entity.User;
import reggie.mapper.UserMapper;
import reggie.service.UserService;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 11:49
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
