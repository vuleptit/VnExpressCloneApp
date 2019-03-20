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
    public void theloai()
    {
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<String> listLink = new ArrayList<>();
        listName.add("Trang chủ");
        listLink.add("https://vnexpress.net/rss/tin-moi-nhat.rss");

        listName.add("Thời sự");
        listLink.add("https://vnexpress.net/rss/thoi-su.rss");

        listName.add("Thế giới");
        listLink.add("https://vnexpress.net/rss/the-gioi.rss");

        listName.add("Kinh doanh");
        listLink.add("https://vnexpress.net/rss/kinh-doanh.rss");

        listName.add("Startup");
        listLink.add("https://vnexpress.net/rss/startup.rss");

        listName.add("Giải trí");
        listLink.add("https://vnexpress.net/rss/giai-tri.rss");

        listName.add("Thể thao");
        listLink.add("https://vnexpress.net/rss/the-thao.rss");

        listName.add("Pháp luật");
        listLink.add("https://vnexpress.net/rss/phap-luat.rss");

        listName.add("Giáo dục");
        listLink.add("https://vnexpress.net/rss/giao-duc.rss");

        listName.add("Sức khỏe");
        listLink.add("https://vnexpress.net/rss/suc-khoe.rss");

        listName.add("Đời sống");
        listLink.add("https://vnexpress.net/rss/gia-dinh.rss");

        listName.add("Du lịch");
        listLink.add("https://vnexpress.net/rss/du-lich.rss");

        listName.add("Khoa học");
        listLink.add("https://vnexpress.net/rss/khoa-hoc.rss");

        listName.add("Số hóa");
        listLink.add("https://vnexpress.net/rss/so-hoa.rss");

        listName.add("Xe");
        listLink.add("https://vnexpress.net/rss/oto-xe-may.rss");

        listName.add("Ý kiến");
        listLink.add("https://vnexpress.net/rss/y-kien.rss");

        listName.add("Tâm sự");
        listLink.add("https://vnexpress.net/rss/tam-su.rss");

        listName.add("Cười");
        listLink.add("https://vnexpress.net/rss/cuoi.rss");
        apiTheLoaiData.setTheLoaiData(listName,listLink);
    }
    public interface ApiTheLoaiData {
        public void setTheLoaiData(ArrayList<String> listName, ArrayList<String> listLink);
    }
}
