package com.ljd.crawler.processor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by peanut on 17/12/21.
 */
public class JsonPipeline extends FilePersistentBase implements Pipeline{
    private PrintWriter printWriter;
    private FileWriter fileWriter;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public JsonPipeline() {
        setPath("/data/webmagic");
    }

    public JsonPipeline(String path) {
        setPath(path);
        try {
            printWriter=new PrintWriter(getFile(path + PATH_SEPERATOR+ "data.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        if(resultItems.getAll().isEmpty() == false){
            printWriter.write(JSON.toJSONString(resultItems.getAll())+"\n");}

    }
}
