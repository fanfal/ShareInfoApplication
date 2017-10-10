package com.au.shareinfoapplication.network;


import java.io.IOException;
import java.util.Map;

public interface HttpInterface {

    HttpResponse get(String url, Map<String, String> customHeaderItems) throws IOException;

    HttpResponse get(String url) throws IOException;

    HttpResponse post(String url, String data) throws IOException;

    HttpResponse head(String url) throws IOException;

    HttpResponse put(String url, String data) throws IOException;

    HttpResponse delete(String url) throws IOException;
}
