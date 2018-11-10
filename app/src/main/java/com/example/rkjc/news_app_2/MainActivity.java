package com.example.rkjc.news_app_2;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rkjc.news_app_2.model.NewsItemResponse;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    RecyclerView mNewsRecyclerView;
    TextView mErrorText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressBar);
        mNewsRecyclerView = findViewById(R.id.news_recyclerview);
        mErrorText = findViewById(R.id.error_text);

        new NewsQueryTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.get_news, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_search) {
            new NewsQueryTask().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class NewsQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mNewsRecyclerView.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                return NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            mProgressBar.setVisibility(View.GONE);
            if (jsonString != null) {

                ArrayList<NewsItemResponse.NewsItem> loNewsItemsNewsItemArrayList = JsonUtils.parseNews(jsonString);
                if (loNewsItemsNewsItemArrayList != null) {
                    mNewsRecyclerView.setVisibility(View.VISIBLE);
                    mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    mNewsRecyclerView.setAdapter(new NewsRecyclerViewAdapter(MainActivity.this,
                            loNewsItemsNewsItemArrayList));
                } else {
                    mErrorText.setVisibility(View.VISIBLE);
                }

            } else {
                mErrorText.setVisibility(View.VISIBLE);
            }
        }
    }
}
