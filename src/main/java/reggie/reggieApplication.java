package reggie;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-19 13:22
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
public class reggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(reggieApplication.class, args);
        log.info("启动成功");
    }
}
