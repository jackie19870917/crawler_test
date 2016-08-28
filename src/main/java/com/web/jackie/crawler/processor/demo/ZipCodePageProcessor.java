package com.web.jackie.crawler.processor.demo;

import com.web.jackie.crawler.util.FileHelper;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipCodePageProcessor implements PageProcessor {

    private Site site = Site.me().setCharset("GB2312").setSleepTime(100);

    private static Pattern patternCity = Pattern.compile("(<td[^<>]*>\\s*<a\\s*href=\"([^<>]+)\">\\s*<b>([^<>]+)</b>\\s*</a>\\s*</td>)");

    private static Pattern pattern = Pattern.compile("(<td[^<>]*>([^<>]+)</td>\\s*<td[^<>]*>\\s*<a[^<>]*>([^<>]+)</a>\\s*</td>\\s*<td[^<>]*>\\s*<a[^<>]*>([^<>]+)</a>\\s*</td>)");

    private static final String FILEPATH = "D:\\webmagic\\mailCode.txt";

    @Override
    public void process(Page page) {
        int priority = (int)page.getRequest().getPriority();
        switch (priority){
            case 0 :
                processProvince(page);
                break;
            case 1:
                processCity(page);
                break;
            case 2:
                processDistrict(page);
                break;

        }
    }

    private void processProvince(Page page) {
        List<String> provinces = page.getHtml().xpath("//*[@id=\"newAlexa\"]/table/tbody/tr/td").all();
        for (String province : provinces) {
            String link = new Html(province).xpath("//a/@href").get();
            String title = new Html(province).xpath("//a/text()").get();
            Request request = new Request(link).setPriority(1).putExtra("province", title);
            page.addTargetRequest(request);
        }
    }

    private void processCity(Page page) {
        String province = page.getRequest().getExtra("province").toString();
        List<String> citys = page.getHtml().xpath("//body/table[@class=t12]/tbody/").all();
        for (String city : citys) {
            Matcher matcher = patternCity.matcher(city);
            while (matcher.find()) {
                String link = matcher.group(2);
                String title = matcher.group(3);
                Request request = new Request(link).setPriority(2).putExtra("city", title).putExtra("province",province);
                page.addTargetRequest(request);
            }
        }
    }

    private void processDistrict(Page page) {
        String province = page.getRequest().getExtra("province").toString();
        String city = page.getRequest().getExtra("city").toString();
        List<String> districts = page.getHtml().xpath("//body/table[@class=t12]/tbody/").all();
        for (String district : districts) {
            Matcher matcher = pattern.matcher(district);
            while (matcher.find()) {
                FileHelper.appendFile(FILEPATH, province + " \t" + city + " \t" + matcher.group(2) + " \t" + matcher.group(3) + " \t" + matcher.group(4) + "\n");
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ZipCodePageProcessor()).addUrl("http://www.ip138.com/post/").scheduler(new LevelLimitScheduler()).run();
    }
}