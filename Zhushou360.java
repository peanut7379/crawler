package com.ljd.crawler.processor;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by peanut on 17/10/12.
 */

public class zhushou360 extends BreadthCrawler {


    private int count;
    public zhushou360(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        //start page
        //addSeed("http://blog.csdn.net/");
        //addSeed("https://mobile.baidu.com/list/soft/recommend");

        //修改位置
        addSeed("http://zhushou.360.cn/list/index/cid/102233/");

        //addRegex("http://blog.csdn.net/.*/article/details/.*");
        addRegex("/list/soft/.*");
        this.count=2;

        //需要抓取图片时设置为true，并加入图片的正则规则
        //setParseImg(true);

        //设置每个线程的抓取间隔（毫秒）
        //setExecuteInterval(1000);

        //设置线程数
        setThreads(30);
    }

    /*
        可以往next中添加希望后续爬取的任务，任务可以是URL或者CrawlDatum
        爬虫不会重复爬取任务，从2.20版之后，爬虫根据CrawlDatum的key去重，而不是URL
        因此如果希望重复爬取某个URL，只要将CrawlDatum的key设置为一个历史中不存在的值即可
        例如增量爬取，可以使用 爬取时间+URL作为key。

        新版本中，可以直接通过 page.select(css选择器)方法来抽取网页中的信息，等价于
        page.getDoc().select(css选择器)方法，page.getDoc()获取到的是Jsoup中的
        Document对象，细节请参考Jsoup教程
    */
    @Override
    public void visit(Page page, CrawlDatums next) {
        if (page.matchUrl(".*/list/index/.*")) {
            //String title = page.select(".applist li a[href *='item']").text();
            Elements titles = page.select("ul.iconList li h3 >a");
            for(int i=0;i<titles.size();i++)
            {
                System.out.println("title:" + titles.get(i).text()); //+ "\tauthor:" + author);
                File file = new File("/Users/peanut/Downloads/360zhushou");
                try{
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file,true);
                    //修改位置
                    fileWriter.write("健康医疗\t"+ titles.get(i).text()+"\n");
                    fileWriter.close();}
                catch(IOException exc){
                    System.out.println("Exception encountered: " + exc);
                }
            }

            //Elements results = page.select("div.page_box a");
            //Element result = results.get(0);
            //CrawlDatum datum = new CrawlDatum(result.attr("abs:href"));
            //修改位置
            CrawlDatum datum = new CrawlDatum("http://zhushou.360.cn/list/index/cid/102233/?page="+String.valueOf(count));
            next.add(datum);
            count++;
        }
    }

    public static void main(String[] args) throws Exception {
        zhushou360 crawler = new zhushou360("crawl", true);
        crawler.start(58);
    }

}
