package reggie.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import reggie.common.BaseContext;
import reggie.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 漫花噬雪
 * 检查是否完成登陆
 * @vreate 2022-05-20 23:58
 */


@Slf4j
@WebFilter(urlPatterns = "/*")
public class loginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    log.info("过滤器生成");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        log.info("拦截到请求{}" ,request.getRequestURI());
        //1.获取本次请求的uri
        String requestURI = request.getRequestURI();
        //定义不需要处理的请求,拦截的是前端的axios请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login",
                "/common/upload",
                "/common/download",
        };
        //2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //3.如果不需要处理，则直接放行
        if (check){
            filterChain.doFilter(request, response);
            return;
        }
        //4.判断登录状态，如果为已登录则放行
        if (request.getSession().getAttribute("employee") != null){
            //将当前id存入线程
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setGetCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }
        //移动端用户登陆也放行
        if (request.getSession().getAttribute("user") != null){
            //将当前id存入线程
            Long user = (Long) request.getSession().getAttribute("user");
            BaseContext.setGetCurrentId(user);

            filterChain.doFilter(request, response);
            return;
        }
        //5.如果未登录则返回登录结果(根据前端跳转需要的数据返回数据，code=0和msg=“NOTLOGIN”)
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    @Override
    public void destroy() {

    }

    //判断是否需要处理
    public boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
