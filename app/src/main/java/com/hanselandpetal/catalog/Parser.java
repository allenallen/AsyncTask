package com.hanselandpetal.catalog;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public static ArrayList<StockModel> parseHtmlString(String htmlDoc){

        Document doc = Jsoup.parse(htmlDoc);

        Elements e = doc.getElementsByClass("p12");

        ArrayList<StockModel> items = new ArrayList<StockModel>();

        for(Element elem : e){
            Elements ef = elem.getElementsByTag("nobr");

            if(ef.size() > 4){
                StockModel m = new StockModel();
                m.setName(ef.get(0).text());
                m.setClose(ef.get(4).text());
                items.add(m);

                Log.i("modeL: ",m.getName() + " " + m.getClose());
            }
        }

        return items;
    }

}
