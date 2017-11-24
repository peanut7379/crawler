这是使用webcollector来爬取网站数据的两个爬虫

其中TutorialCrawler.java是爬取百度手机助手的软件分类列表，输出到文本文件，如社交通讯\t新浪微博，中间用tab键隔开

zhushou360.java是爬取360手机助手

------------------------------

重新编写了TutorialCrawler.java，增加了使用addRegex()正则自动匹配所有代表分类主题的页面，进行广度遍历，并使输出结果更加自动化

------------------------------
### bmpProxy.java
拦截网站cookie的demo，代码中以猎聘网为例
工具：
[browsermob-proxy](https://github.com/lightbody/browsermob-proxy)
SwitchyOmega

## 用法：
在谷歌浏览器中使用SwitchyOmega设置代理，使用browsermob-proxy开启代理端口，拦截请求，将已登录的cookie写入文件

# ssl配置：
默认不配置的情况下，对于非SSL的页面是可以随便访问的。但是对于SSL站点，会出现不是私密连接的告警，甚至直接打不开。
# 解决：
需要安装[证书](https://github.com/lightbody/browsermob-proxy/blob/master/browsermob-core/src/main/resources/sslSupport/ca-certificate-rsa.cer)
下载这个文件，存到系统中。在Mac下打开“钥匙串访问”，将这个文件拖入docker图标中。
然后双击打开证书详情，并选择始终信任。然后就可以直接访问SSL站点了。不过这个证书用作测试可以的，长期信任并不是一个好做法。这是一个公开证书，很容易被他人盗用。所以比较合理的方式是生成自己的证书。
