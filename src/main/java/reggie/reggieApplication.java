package reggie;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-19 13:22
 */
@Slf4j
@ServletComponentScan
@EnableCaching//开启chche注解功能
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class reggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(reggieApplication.class, args);
        log.info("启动成功");
    }
}
