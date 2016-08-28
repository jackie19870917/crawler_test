package com.web.jackie.crawler.util;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/8/28.
 */
public class FileHelper {

    /**
     * 下载文件
     * @param filePath filePath
     * @param urlList urlList
     * @param suffix suffix
     */
    public static void downloadFile(String filePath, List<String> urlList, String suffix) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //这里先判断文件夹名是否存在，不存在则建立相应文件夹
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Files.createParentDirs(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //这里通过httpclient下载之前抓取到的图片网址，并放在对应的文件中
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);
            InputStream in = null;
            try {
                HttpGet httpget = new HttpGet(url);
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                in = entity.getContent();
                File tagerFile = new File(filePath + "/" + getRandomString(10) + suffix);
                FileOutputStream fout = new FileOutputStream(tagerFile);
                int l = -1;
                byte[] tmp = new byte[1024];
                while ((l = in.read(tmp)) != -1) {
                    fout.write(tmp, 0, l);
                }
                fout.flush();
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try{
            httpclient.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    //length表示生成字符串的长度
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static void writeFile(String filePath,String context){
        File file = new File(filePath);
        try {
            Files.createParentDirs(file);
            Files.touch(file);
            Files.write(context,file, Charsets.UTF_8);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void appendFile(String filePath,String context){
        File file = new File(filePath);
        try {
            Files.createParentDirs(file);
            Files.touch(file);
            Files.append(context,file, Charsets.UTF_8);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
