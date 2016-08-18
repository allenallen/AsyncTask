package com.hanselandpetal.catalog;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class HttpManager {

    public static HashMap<String,String> getData(String uri){

        AndroidHttpClient client = AndroidHttpClient.newInstance("AnAgent");
        HttpGet request = new HttpGet(uri);
        HttpResponse response;

        try{
            response = client.execute(request);
            String hResponse = EntityUtils.toString(response.getEntity());

            HashMap<String,String> map = (HashMap<String,String>) Parser.parseHtmlString(hResponse);
            return map;
            }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            client.close();
        }

    }

}
