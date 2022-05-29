package reggie.util;

import org.apache.commons.mail.HtmlEmail;

import java.util.Properties;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-25 10:34
 */
public class SendEmailCode {
    public static void send(String code,String email){
        Properties p = new Properties();
        try {
            HtmlEmail send = new HtmlEmail();
            // 固定值，QQ邮箱服务
            send.setHostName("smtp.qq.com");
            // 固定值，QQ邮箱端口号
            send.setSmtpPort(465);
            send.setCharset("utf-8");
            send.setSSL(true);
            // 接收者的Eamil
            send.addTo(email);
//            send.addTo("1993736630@qq.com");
            // 参数1：发送者的QQEamil，参数2：发送者显示名字
            send.setFrom("270992941@qq.com", "验证码服务");
            // 参数1：发送者的QQEmail，参数2：第一步获取的授权码
            send.setAuthentication("270992941@qq.com", "sgenwwakmxzabjfc");
            // 邮件标题
            send.setSubject("验证码");
            // 邮件内容
            send.setMsg("你好！验证码是"+ code);
            send.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
