package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.AddressBook;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 15:45
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
