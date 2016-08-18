package com.hanselandpetal.catalog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static Map<String, String> parseHtmlString(String htmlDoc){

        Map<String,String> sMap = new HashMap<String, String>();
        Document doc = Jsoup.parse(htmlDoc);

        Elements e = doc.getElementsByClass("p12");
        for(Element elem : e){
            Elements ef = elem.getElementsByTag("nobr");

            if(ef.size() > 4){
                sMap.put(ef.get(0).text(),ef.get(4).text());
            }
        }

        return sMap;
    }

}
