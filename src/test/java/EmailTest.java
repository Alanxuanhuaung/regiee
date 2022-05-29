import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import reggie.reggieApplication;
import reggie.util.SendEmailCode;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 10:40
 */
@SpringBootTest(classes = reggieApplication.class)
public class EmailTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("city", "beijing");
        System.out.println(valueOperations.get("city"));
    }

}
