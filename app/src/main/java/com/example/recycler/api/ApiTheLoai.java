package com.example.recycler.api;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ApiTheLoai {
    private ApiTheLoaiData apiTheLoaiData;

    public ApiTheLoai(ApiTheLoaiData apiTheLoaiData) {
        this.apiTheLoaiData = apiTheLoaiData;
    }

    public void getData(final String link) {
        AndroidNetworking.get(link).addQueryParameter("limit", "3").addHeaders("token", "1234").setTag("test")
                .setPriority(Priority.HIGH)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                ArrayList<String> listName = new ArrayList<>();
                ArrayList<String> listLink = new ArrayList<>();
                Document document = Jsoup.parse(response);
                Element sidebar = document.getElementsByClass("sidebar_1").first();
                Elements ss = sidebar.children().select("ul.list_rss > *");

                for (Element elements : ss) {
                    if (elements.getElementsByClass("rss_txt").first() != null) {

                        listLink.add("https://vnexpress.net"+elements.getElementsByClass("rss_txt").first().attr("href"));
                        listName.add(elements.getElementsByClass("rss_txt").first().ownText());
                    }

                }
                apiTheLoaiData.setTheLoaiData(listName,listLink);

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    public interface ApiTheLoaiData {
        public void setTheLoaiData(ArrayList<String> listName, ArrayList<String> listLink);
    }
}
