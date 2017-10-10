package com.au.shareinfoapplication.network;


import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;

public class SIHttpUtil implements HttpInterface {
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public HttpResponse get(String url, Map<String, String> customHeaderItems) throws IOException {
        return null;
    }

    @Override
    public HttpResponse get(String url) throws IOException {
        return null;
    }

    @Override
    public HttpResponse post(String url, String data) throws IOException {
        return null;
    }

    @Override
    public HttpResponse head(String url) throws IOException {
        return null;
    }

    @Override
    public HttpResponse put(String url, String data) throws IOException {
        return null;
    }

    @Override
    public HttpResponse delete(String url) throws IOException {
        return null;
    }
}
