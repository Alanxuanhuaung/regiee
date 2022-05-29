package reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import reggie.common.JacksonObjectMapper;

import java.util.List;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-19 13:49
 */
@Slf4j
@Configuration//配置注解
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源加载映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器
        MappingJackson2HttpMessageConverter MessageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson
        MessageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的转换器追加进mvc
        converters.add(0, MessageConverter);
    }
}
