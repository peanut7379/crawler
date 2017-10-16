
package cn.edu.hfut.dmic.webcollector.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;


/**
 * WebCollector 2.x版本的tutorial(2.20以上) 
 * 2.x版本特性：
 * 1）自定义遍历策略，可完成更为复杂的遍历业务，例如分页、AJAX
 * 2）可以为每个URL设置附加信息(MetaData)，利用附加信息可以完成很多复杂业务，例如深度获取、锚文本获取、引用页面获取、POST参数传递、增量更新等。
 * 3）使用插件机制，WebCollector内置两套插件。
 * 4）内置一套基于内存的插件（RamCrawler)，不依赖文件系统或数据库，适合一次性爬取，例如实时爬取搜索引擎。
 * 5）内置一套基于Berkeley DB（BreadthCrawler)的插件：适合处理长期和大量级的任务，并具有断点爬取功能，不会因为宕机、关闭导致数据丢失。 
 * 6）集成selenium，可以对javascript生成信息进行抽取
 * 7）可轻松自定义http请求，并内置多代理随机切换功能。 可通过定义http请求实现模拟登录。 
 * 8）使用slf4j作为日志门面，可对接多种日志
 *
 * 可在cn.edu.hfut.dmic.webcollector.example包中找到例子(Demo)
 *
 * @author hu
 */
public class TutorialCrawler extends BreadthCrawler {

    /*
        该例子利用正则控制爬虫的遍历，
        另一种常用遍历方法可参考DemoTypeCrawler
    */
    
    public TutorialCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        //start page
        //baidu助手
        //addSeed("https://mobile.baidu.com/list/soft/recommend");
        //应用宝
        addSeed("http://sj.qq.com/myapp/category.htm?orgame=1");
        //华为
        addSeed("http://app.hicloud.com/soft/list");



        //应用宝
        addRegex(".*orgame=1&categoryId=.*");
        //华为
        addRegex(".*/soft/list_.*");
        
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
        if (page.matchUrl(".*/soft/list_.*")) {
            //String title = page.select(".applist li a[href *='item']").text();

            //baidu助手
            //Elements titles = page.select(".applist li a[href *='item']");
            //应用宝
            //Elements titles = page.select(".app-info-desc a.name.ofh");
            //华为
            Elements titles = page.select("h4.title a");

            for(int i=0;i<titles.size();i++)
            {
                System.out.println("title:" + titles.get(i).text()); //+ "\tauthor:" + author);
                //应用宝分类名
                //String id = page.url().substring(page.url().indexOf("Id=")+3);
                //String category = page.select("li#cate-"+id).text();
                //华为分类名
                String category = page.select("span.key-select.txt-sml").text();


                File file = new File("/Users/peanut/Downloads/huawei");
                try{
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file,true);
                    fileWriter.write(category+"\t"+ titles.get(i).text()+"\n");
                    fileWriter.close();}
                catch(IOException exc){
                    System.out.println("Exception encountered: " + exc);
                }
            }

            //Elements results = page.select("div .pagination >a");
            //Element result = results.get(0);
            //CrawlDatum datum = new CrawlDatum(result.attr("abs:href"));
            //next.add(datum);
        }
    }

    public static void main(String[] args) throws Exception {
        TutorialCrawler crawler = new TutorialCrawler("crawl", true);
        crawler.start(30);
    }

}
