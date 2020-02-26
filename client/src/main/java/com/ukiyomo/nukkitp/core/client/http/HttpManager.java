package com.ukiyomo.nukkitp.core.client.http;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpManager {

    private OkHttpClient okHttpClient = new OkHttpClient();

    public void doGet(String url, HttpRequestSuccess success, HttpRequestFailure failure) {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = okHttpClient.newBuilder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(8000, TimeUnit.MILLISECONDS)
                .build()
                .newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                failure.callback();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                success.callback(response);
            }
        });
    }

    public void doPost(String url, RequestBody requestBody, HttpRequestSuccess success, HttpRequestFailure failure) {

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newBuilder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(8000, TimeUnit.MILLISECONDS)
                .build()
                .newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                failure.callback();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                success.callback(response);

            }
        });

    }
}
