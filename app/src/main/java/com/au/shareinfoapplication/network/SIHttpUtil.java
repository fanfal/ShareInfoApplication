package com.au.shareinfoapplication.network;


import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.au.shareinfoapplication.network.ResponseConvertor.convertResponse;

public class SIHttpUtil implements HttpInterface {
    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient okHttpClient;

    @Inject
    public SIHttpUtil(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public HttpResponse get(String url, Map<String, String> customHeaderItems) throws IOException {
        Request.Builder builder = new Request.Builder();
        insertHeader(builder, customHeaderItems);
        Request request = builder.get().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        return convertResponse(response);
    }

    @Override
    public HttpResponse get(String url) throws IOException {
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        return convertResponse(response);
    }

    @Override
    public HttpResponse post(String url, String data) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON_TYPE, data);
        Request.Builder builder = new Request.Builder();
        Request request = builder.post(requestBody).url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        return convertResponse(response);
    }

    @Override
    public HttpResponse head(String url) throws IOException {
        return null;
    }

    @Override
    public HttpResponse put(String url, String data) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON_TYPE, data);
        Request.Builder builder = new Request.Builder();
        Request request = builder.put(requestBody).url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        return convertResponse(response);
    }

    @Override
    public HttpResponse delete(String url) throws IOException {
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        return convertResponse(response);
    }

    private void insertHeader(Request.Builder requestBuilder, Map<String, String> headerItems) {
        for (String key : headerItems.keySet()) {
            requestBuilder.addHeader(key, headerItems.get(key));
        }
    }
}
