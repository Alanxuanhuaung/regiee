package reggie.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reggie.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 漫花噬雪
 * @vreate 2022-05-23 19:36
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    //文件上传
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //用uuid重新生成文件名避免重复名称
        String fillname = UUID.randomUUID().toString() + suffix;
        //创建目标目录的对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if(!dir.exists()){
            //不存在，创建目录
            dir.mkdirs();
        }
        //将上传的文件转存位置
        file.transferTo(new File(basePath + fillname));
        return R.success(fillname);
    }

    //下载功能
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        //输入流，读取文件内
        FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

        //输出流，写回浏览器，在浏览器展示图片
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("image/jpeg");

        //定义长度，byte数组，每读取1024长度后写入byte数组并将长度length赋值，length！=-1代表没读完
        //边读边写
        int length = 0;
        byte[] bytes = new byte[1024];
        while ((length = fileInputStream.read(bytes)) !=-1){
            outputStream.write(bytes, 0, length);
            outputStream.flush();
        }
        outputStream.close();
        fileInputStream.close();
    }
}
