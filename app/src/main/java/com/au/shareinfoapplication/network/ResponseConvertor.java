package com.au.shareinfoapplication.network;


import java.io.IOException;

import okhttp3.Response;

public class ResponseConvertor {
    public static HttpResponse convertResponse(Response response) throws IOException {
        return new HttpResponse(response.code(), null, response.request().toString(), response.body().string());
    }
}
