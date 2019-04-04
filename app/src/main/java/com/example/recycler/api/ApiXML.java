package com.example.recycler.api;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.recycler.entity1.RssItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ApiXML {
    private DataApiXML dataApiXML;
    private DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    private static final String TAG = "ApiXML";
    public void getDataXML(final String link){
        AndroidNetworking.get(link).addQueryParameter("limit", "3").addHeaders("token", "1234").setTag("test")
                .setPriority(Priority.HIGH)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
              //  Log.d("test", response);
                try {
                    String nodeName;
                    ArrayList<RssItem> list = new ArrayList<>();
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    InputSource is = new InputSource(new StringReader(response));
                    Document document = builder.parse(is);
                    NodeList nodeList = document.getElementsByTagName("item");
                   // Log.d("nam", nodeList.item(0).getChildNodes().getLength()+"");
                    for(int i=0;i<nodeList.getLength();i++){
                        NodeList listItem = nodeList.item(i).getChildNodes();
                        RssItem rssItem = new RssItem();
                        for(int j=0;j<listItem.getLength();j++){
                            Node item = listItem.item(j);
                            nodeName = item.getNodeName();
                            try {
                                if ("description".equals(nodeName)){
                                    String s = item.getLastChild().getNodeValue();
                                    //Log.d("data", s+"\n");
                                    getJsoupData(rssItem,s);
                                     //Log.d("nam", getJsoupData(s));
                                }
                                else if("title".equals(nodeName)){
                                    String s = item.getLastChild().getNodeValue();
                                    if(s==null) s ="";
                                    rssItem.setTitle(s);
                                }
                                else if("pubDate".equals(nodeName)){
                                    String s = item.getLastChild().getNodeValue();
                                    rssItem.setDate(setDate(s));
//                                Log.d("nam",s);
                                }
                                else if("link".equals(nodeName)){
                                    String s = item.getLastChild().getNodeValue();
                                    rssItem.setLinkDetail(s);
                                }
                            }catch (Exception e){
                                rssItem.setDescription("");
                                rssItem.setLinkImage("");
                                rssItem.setTitle("");
                                rssItem.setDate(setDate("Wed, 13 Mar 2000 00:00:00 +0700"));
                                rssItem.setLinkDetail("");
                            }

                        }
                        if(rssItem.getTitle()!=""){
                            list.add(rssItem);
                        }


                    }
                    dataApiXML.setData(list);
                } catch (ParserConfigurationException e) {
                    Log.d(TAG, "onResponse: ParserConfigurationException");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "onResponse: IOException");
                    e.printStackTrace();
                } catch (SAXException e) {
                    Log.d(TAG, "onResponse: onResponse");
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }
    public void getJsoupData(RssItem rssItem,String s){
        org.jsoup.nodes.Document document = Jsoup.parse(s);
        Element element = document.getElementsByTag("img").first();
        String linkimage = element.attr("src");
        String description = s.substring(s.indexOf("</br>")+5);
        rssItem.setDescription(description);
        rssItem.setLinkImage(linkimage);

    }
    public Date setDate(String date) {
        Date date1 = null;
        try {
            date1 = dateFormat.parse(date);
             return date1;


        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("nam", e.getMessage());
        }
        return new Date();
    }
    public void setDataApiXML(DataApiXML dataApiXML){
        this.dataApiXML = dataApiXML;
    }
    public interface DataApiXML{
        public void setData(ArrayList<RssItem> listRssItem);
    }
}
