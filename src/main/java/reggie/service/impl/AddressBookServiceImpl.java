package reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.entity.AddressBook;
import reggie.mapper.AddressBookMapper;
import reggie.service.AddressBookService;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 15:46
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
