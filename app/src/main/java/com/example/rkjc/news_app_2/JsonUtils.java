package com.example.rkjc.news_app_2;

import android.util.Log;

import com.example.rkjc.news_app_2.model.NewsItemResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static ArrayList<NewsItemResponse.NewsItem> parseNews(String JSONString) {

        try {
            Gson loGson = new Gson();
            NewsItemResponse loNewsItemResponse = loGson.fromJson(JSONString, NewsItemResponse.class);
            return loNewsItemResponse.getArticles();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return  null;
    }

}


