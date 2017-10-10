package com.au.shareinfoapplication.network;


import okhttp3.Response;

public class ResponseConvertor {
    public static HttpResponse convertResponse(Response response) {
        return new HttpResponse(response.code(), null, response.request().toString(), response.body().toString());
    }
}
