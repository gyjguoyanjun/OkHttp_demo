package com.gyj.okhttp_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getTongbu();
        //getYibu();
        //postTongbu();
        postYibu();
    }

    //GET同步
    public void getTongbu() {
        final OkHttpClient mClient = new OkHttpClient();
        final HttpUrl httpUrl = HttpUrl.parse(UrlUtils.URL).newBuilder()
                .addQueryParameter("uri", "tt")
                .build();

        final Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = mClient.newCall(request).execute();
                    Log.d("gyj=========", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //GET异步
    public void getYibu() {
        final OkHttpClient mClient = new OkHttpClient();
        final HttpUrl httpUrl = HttpUrl.parse(UrlUtils.URL).newBuilder()
                .addQueryParameter("uri", "tt")
                .build();

        final Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("gyj===========", response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    //Post同步
    public void postTongbu() {
        final OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("processID", "getNavigateNews")
                .add("page", "1")
                .add("code", "news")
                .add("pageSize", "20")
                .add("parentid", "0")
                .add("type", "1")
                .build();
        final Request request = new Request.Builder()
                .url(UrlUtils.URLPOST)
                .post(body)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response execute = client.newCall(request).execute();
                    String string = execute.body().string();
                    Log.d("gyj========", string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Post异步
    private void postYibu() {
        final OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("processID", "getNavigateNews")
                .add("page", "1")
                .add("code", "news")
                .add("pageSize", "20")
                .add("parentid", "0")
                .add("type", "1")
                .build();
        final Request request = new Request.Builder()
                .url(UrlUtils.URLPOST)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("gyj========", string);
                    }
                });
            }

        });
    }
}
