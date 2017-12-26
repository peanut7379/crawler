package com.ljd.crawler.processor;


import io.netty.handler.codec.http.HttpMethod;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.*;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.core.har.HarNameVersion;
import net.lightbody.bmp.core.har.HarPage;
import net.lightbody.bmp.filters.RequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Created by peanut on 17/11/22.
 */
public class bmpProxy {
    private static Logger logger = LoggerFactory.getLogger(bmpProxy.class);
    public static BrowserMobProxy proxy;
    public static int proxyPort;

    public void startProxy() {
        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start(10000);
        proxyPort = proxy.getPort();

        logger.info("Proxy started @port {}", proxyPort);



        proxy.addRequestFilter((request, contents, messageInfo) -> {
            if(messageInfo.getUrl().contains("c.liepin.com")) {
                if(request.getUri().equals("/")){
                if (!request.headers().get("Cookie").isEmpty()) {
                    System.out.println(request.getUri() + " --->> " + request.headers().get("Cookie"));
                    File file = new File("/Users/peanut/Downloads/liepinCookie");
                    try{
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileWriter fileWriter = new FileWriter(file,true);
                        fileWriter.write("Cookie "+request.headers().get("Cookie")+"\n");
                        fileWriter.close();}
                    catch(IOException exc){
                        System.out.println("Exception encountered: " + exc);
                    }
                }
            }
            }
            return null;
        });



    }

    public static void main(String[] args) {
        bmpProxy bmp = new bmpProxy();
        bmp.startProxy();
    }
}
