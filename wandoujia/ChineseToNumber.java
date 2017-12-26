package com.ljd.crawler.processor;

import javax.tools.Tool;

/**
 * Created by peanut on 17/12/22.
 */
public class ChineseToNumber {
    public String trans(String str){
        String str1 = new String();
        String str2 = new String();
        String str3 = new String();
        int k = 0;
        boolean dealflag = true;
        String chineseNum = str;
        Long result = Long.valueOf(0);
        for(int i=0;i<chineseNum.length();i++){
            if(chineseNum.charAt(i) == '亿'){
                str1 = chineseNum.substring(0,i);//截取亿前面的数字，逐个对照表格，然后转换
                k = i+1;
                dealflag = false;//已经处理
            }
            if(chineseNum.charAt(i) == '万'){
                str2 = chineseNum.substring(k,i);
                str3 = str.substring(i+1);
                dealflag = false;//已经处理
            }
        }
        if(dealflag){//如果没有处理
            str3 =  chineseNum;
        }

        if(str1.isEmpty() == false)
            result= result+(long)(Double.parseDouble(str1) * 100000000);
        if(str2.isEmpty() == false)
            result= result+(long)(Double.parseDouble(str2) * 10000);
        if(str3.isEmpty() == false)
            result= result+(long)(Double.parseDouble(str3));
        return result.toString();
    }
    public static void main(String[] args){
        ChineseToNumber a =new ChineseToNumber();
        System.out.println(a.trans("100.1"));
    }

}
