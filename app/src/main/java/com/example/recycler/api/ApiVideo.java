package com.example.recycler.api;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.recycler.entity1.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ApiVideo {
    private DataApiVideo data;
    private DateFormat dateFormat;
    private static final String TAG = "ApiVideo";
    public ApiVideo(DataApiVideo data) {
        this.data = data;

        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void getData(final String link) {
        Log.d(TAG, "onResponse: "+link);
        AndroidNetworking.get(link).addQueryParameter("limit", "3").addHeaders("token", "1234").setTag("test")
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<Video> list =new ArrayList<>();
                    JSONArray items = response.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String title = item.getString("title");
                        String linkImgae = item.getString("avatar");
                        String linkVideo = item.getString("file_mp4");
                        String date = item.getString("date_published");
                        Video video= new Video(title,linkImgae,linkVideo);
                        video.setDate(dateFormat.parse(date));
                        list.add(video);


                    }
                    Log.d(TAG, "onResponse: "+response);
                    data.setData(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    public interface DataApiVideo {
        void setData(ArrayList<Video> listVideo);
    }
}
