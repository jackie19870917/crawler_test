package com.web.jackie.crawler.processor.meizitu;

import us.codecraft.webmagic.Spider;

public class MeizituImgSpiderTest {
    public static void main(String[] args) {
        String fileStorePath = "D:\\webmagic-lib\\data\\imgNew7\\";
        String urlPattern = "http://www.meizitu.com/[a-z]/[0-9]{1,4}.html";
        MeizituImgProcessor imgspider = new MeizituImgProcessor("http://www.meizitu.com/", urlPattern);
        //webmagic采集图片代码演示，相关网站仅做代码测试之用,请勿过量采集
        Spider.create(imgspider)
                .addUrl("http://www.meizitu.com/")
                .addPipeline(new MeizituImgPipeline(fileStorePath))
                .thread(10)       //此处线程数可调节
                .run();
    }

}