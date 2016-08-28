package com.web.jackie.crawler.processor.meizitu;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.List;

public class MeizituImgProcessor implements PageProcessor {
    private String urlPattern;
    private Site site;

    public MeizituImgProcessor(String startUrl, String urlPattern) {
        this.site = Site.me().setDomain(UrlUtils.getDomain(startUrl));
        this.urlPattern = urlPattern;
    }

    @Override
    public void process(Page page) {
        String imgRegex = "http://www.meizitu.com/wp-content/uploads/20[0-9]{2}[a-z]/[0-9]{1,4}/[0-9]{1,4}/[0-9]{1,4}.jpg";
        List<String> requests = page.getHtml().links().regex(urlPattern).all();
        page.addTargetRequests(requests);
        System.out.println(page.getHtml().xpath("div[@class=picture]"));
        // div[@class=picture]
        List<String> listProcess = page.getHtml().xpath("div#picture").regex(imgRegex).all();
        System.out.println(listProcess);
//        page.putField("img", listProcess);
    }

    @Override
    public Site getSite() {
        return site;
    }
}