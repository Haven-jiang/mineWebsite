package com.Haven.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载文件 DownloadUtil
 *
 * @author HavenJust
 * @date 22:10 周日 24 四月 2022年
 */

public class DownloadUtil {


    public static String downloadFile(String fileUrl,String saveUrl) {
        HttpURLConnection httpUrl = null;
        byte[] buf = new byte[1024];
        int size = 0;
        try {
            //下载的地址
            URL url = new URL(fileUrl);
            //支持http特定功能
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            //缓存输入流,提供了一个缓存数组,每次调用read的时候会先尝试从缓存区读取数据
            BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
            File file = new File(saveUrl);
            //判断文件夹是否存在
            if(!file.exists()){
                file.mkdir();//如果不存在就创建一个文件夹
            }
            //讲http上面的地址拆分成数组,
            String arrUrl[] = fileUrl.split("/");
            //输出流,输出到新的地址上面
            FileOutputStream fos = new FileOutputStream(saveUrl+"/"+arrUrl[arrUrl.length-1]);
            while ((size = bis.read(buf)) != -1){
                fos.write(buf, 0, size);
            }
            //记得及时释放资源
            fos.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpUrl.disconnect();
        return null;
    }

}
