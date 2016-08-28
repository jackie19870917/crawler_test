package com.web.jackie.crawler.processor.tuduowan;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2016/8/28.
 */
public class TuDuowanProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        try {
            //http://s1.dwstatic.com/group1/M00/AC/07/fbec941ab1bcaf8da7b578f573e242be.jpg
            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"i-list\"]").regex("http://s1.dwstatic.com/group1/M00/[A-Z|0-9]+/[A-Z|0-9]+/[a-z|0-9]+.jpg").all());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TuDuowanProcessor()).
                addUrl("http://tu.duowan.com/m/meinv").
                addPipeline(new TuDuowanPipeline("D:\\webmagic\\")).
                thread(5).
                run();
    }
}
