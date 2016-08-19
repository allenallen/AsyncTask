package com.hanselandpetal.catalog;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

//        Document doc = Jsoup.parse(htmlDoc);
//
//        Elements e = doc.getElementsByClass("p12");
//
        ArrayList<StockModel> items = new ArrayList<StockModel>();
//
//        for(Element elem : e){
//            Elements ef = elem.getElementsByTag("nobr");
//
//            if(ef.size() > 4){
//                StockModel m = new StockModel();
//                m.setName(ef.get(0).text());
//                m.setClose(ef.get(4).text());
//                items.add(m);
//
//                Log.i("modeL: ",m.getName() + " " + m.getClose());
//            }
//        }

        try {
            JSONObject baseJson = new JSONObject(htmlDoc);

            JSONArray ar = baseJson.getJSONArray("stock");

            JSONObject item = null;
            StockModel model = null;
            for (int i = 0; i < ar.length(); i++){
                item = ar.getJSONObject(i);
                model = new StockModel();

                model.setName(item.getString("name"));

                JSONObject priceObject = item.getJSONObject("price");

                model.setPrice(priceObject.getDouble("amount"));
                model.setPctChange(item.getDouble("percent_change"));

                items.add(model);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return items;
    }

}
