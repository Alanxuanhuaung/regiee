package reggie.common;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-22 23:39
 */
//保存和取出当前线程的用户id
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setGetCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
