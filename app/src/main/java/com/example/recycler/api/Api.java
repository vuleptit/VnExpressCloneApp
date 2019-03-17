package com.example.recycler.api;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.recycler.entity1.Content;
import com.example.recycler.State;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Api {
    private Context context;
    private ApiData apiData;

    public Api(Context context) {
        this.context = context;
    }


    public void getData(final String link) {
        AndroidNetworking.get(link).addQueryParameter("limit", "3").addHeaders("token", "1234").setTag("test")
                .setPriority(Priority.HIGH)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                ArrayList<Content> list = new ArrayList<>();
                try {


                    Document document = Jsoup.parse(response);
                    if (link.contains("e.vnexpress.net")) {
                        Element sidebar = document.getElementsByClass("main_detail_page").first();
                        list.add(new Content(State.STATE_TEXT_TITLE, sidebar.getElementsByClass("block_title_detail").first().text()));
                        list.add(new Content(State.STATE_TEXT_TIME, sidebar.getElementsByClass("author").first().text()));
                        list.add(new Content(State.STATE_TEXT_DESCRIPTION, sidebar.getElementsByClass("lead_post_detail row").first().text()));
                        Element element = sidebar.getElementsByClass("fck_detail").first();
                        if (sidebar.select("div.thumb_detail_top").first() != null) {
                            {
                                String src = sidebar.select("div.thumb_detail_top > img").first().attr("src");
                                String alt = sidebar.select("div.thumb_detail_top > img").first().attr("alt");
                                list.add(new Content(State.STATE_IMAGE, src, alt));
                                Log.d("test", src);
                            }
                        }
                        Elements ss = element.children();

                        for (Element elements : ss) {
                            if (elements.select("p").first() != null) {
                                String text = elements.select("p").first().html();
                                Log.d("test", text);
                                list.add(new Content(State.STATE_TEXT_DETAIL, text));
                            }

                        }
                    } else {

                        Element sidebar = document.getElementsByClass("sidebar_1").first();

                        list.add(new Content(State.STATE_TEXT_TITLE, sidebar.getElementsByClass("title_news_detail").first().text()));
                        list.add(new Content(State.STATE_TEXT_TIME, sidebar.getElementsByClass("time").first().text()));
                        list.add(new Content(State.STATE_TEXT_DESCRIPTION, sidebar.getElementsByClass("description").first().text()));
                        Element element = sidebar.getElementsByTag("article").first();
                        Elements ss = element.children();

                        for (Element elements : ss) {
//                    Element elements1 = elements.select("tbody > tr > td > img").first();
                            if (elements.select("p").first() != null) {
                                String text = elements.select("p").first().html();
                                Log.d("test", text);
                                list.add(new Content(State.STATE_TEXT_DETAIL, text));
                            }
                            if (elements.select("table").first() != null) {
                                {
                                    String src = elements.select("table > tbody > tr > td > img").first().attr("src");
                                    String alt = elements.select("table > tbody > tr > td > img").first().attr("alt");
                                    list.add(new Content(State.STATE_IMAGE, src, alt));
                                    Log.d("test", src);
                                }
                            }
                        }

                    }
                } catch (Exception e) {
                    e.getMessage();
                    list.add(new Content(State.STATE_TEXT_DETAIL, "không lấy được dữ liệu"));
                }
                apiData.setData(list);
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    public void setApiData(ApiData apiData) {
        this.apiData = apiData;
    }

    public interface ApiData {
        public void setData(ArrayList<Content> listContent);
    }
}
