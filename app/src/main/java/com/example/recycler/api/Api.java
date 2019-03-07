package com.example.recycler.api;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Api {
    String link;
    private Context context;
    public Api(Context context,String link) {
        this.link = link;
        this.context = context;
    }


    public void getData() {
        AndroidNetworking.get("https://vnexpress.net/the-gioi/yeu-to-khien-trieu-tien-kho-lam-cang-voi-my-3890813.html").addQueryParameter("limit", "3").addHeaders("token", "1234").setTag("test")
                .setPriority(Priority.LOW)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                Elements elements = document.getElementsByTag("article");
                Elements elements1 = elements.get(0).select("table>tbody >tr>td>img");
                String s = elements1.get(0).attr("alt");
                Log.d("test", s);

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }
}
