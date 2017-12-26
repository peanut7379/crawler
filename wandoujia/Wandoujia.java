package com.ljd.crawler.processor;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by peanut on 17/12/20.
 */
public class Wandoujia implements PageProcessor{

        private static Logger logger = LoggerFactory.getLogger(DouyinMusicRankListPageProcessor.class);
        private static String baseUrl = "http://www.wandoujia.com/category/game";
        private static ChineseToNumber change=new ChineseToNumber();
        Site site =  Site.me().setSleepTime(3);

        public void process(Page page) {
            if(page.getUrl().regex("(http://www.wandoujia.com/apps/\\w+)").toString() != null){
        Html html= page.getHtml();
            //String title =html.xpath("//span[@class='title']/text()").toString();
             //System.out.println("app名字\t" + title+"\n");
            page.putField("appName",html.xpath("//p[@class=app-name]/span[@class='title']/text()").get());
            page.putField("packageName",page.getUrl().regex("/apps/(.*)").get());
            page.putField("firstClassification",html.xpath("//div[@class='second']/a/span/text()").get());
            page.putField("SecondaryClassification",html.xpath("//dd[@class='tag-box']/a/allText()").all());
            page.putField("Tag",html.xpath("//div[@class='side-tags clearfix']/div/a/text()").all());
            page.putField("InstallationNumber",change.trans(html.xpath("//span[@class='item install']/i/text()").get()));
            page.putField("Developer",html.xpath("//span[@class='dev-sites']/text()").get());
            }
            page.addTargetRequests(page.getHtml().links().regex("http://www.wandoujia.com/category/\\d+.*").all());
            page.addTargetRequests(page.getHtml().links().regex("http://www.wandoujia.com/apps/[\\.a-zA-Z\\_0-9]*").all());

        }

        public Site getSite() {
            return this.site;
        }

        public static void main(String[] args) {
            String url ="http://www.wandoujia.com/category/app";
            Request request = new Request(url);


            Spider.create(new Wandoujia())
                    .addUrl(baseUrl)
                    .addRequest(request).thread(3).addPipeline(new JsonPipeline("/Users/peanut/Downloads/wandoujia"))
                    .addPipeline(new ConsolePipeline())
                    .run();
        }
    }
