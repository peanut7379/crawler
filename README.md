这是使用webcollector来爬取网站数据的两个爬虫

其中TutorialCrawler.java是爬取百度手机助手的软件分类列表，输出到文本文件，如社交通讯\t新浪微博，中间用tab键隔开

zhushou360.java是爬取360手机助手

------------------------------

重新编写了TutorialCrawler.java，增加了使用addRegex()正则自动匹配所有代表分类主题的页面，进行广度遍历，并使输出结果更加自动化
