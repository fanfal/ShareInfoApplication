package com.au.shareinfoapplication.network;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.Map;


public class HttpResponse {
    private int responseCode;
    private Map<String, String> headers;
    private String requestString;
    private String responseString;
    private boolean isFromCache;

    public HttpResponse(int responseCode, Map<String, String> headers, String requestString, String responseString) {
        this(responseCode, headers, requestString, responseString, false);
    }

    public HttpResponse(int responseCode, Map<String, String> headers, String requestString, String responseString, boolean isFromCache) {
        this.responseCode = responseCode;
        this.headers = headers;
        this.requestString = requestString;
        this.responseString = responseString;
        this.isFromCache = isFromCache;
    }

    public boolean isSuccessful() {
        return responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE;
    }

    public int getCode() {
        return responseCode;
    }

    public Reader getCharStream() throws IOException {
        return new StringReader(responseString);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public boolean isFromCache() {
        return isFromCache;
    }

    public String getResponseString() {
        return responseString;
    }

    @Override
    public String toString() {
        return "HttpResponse{" + "responseCode=" + responseCode + ", headers="
                + headers + ", requestString='" + requestString + ", responseString='"
                + responseString + ", isFromCache=" + isFromCache + '}';
    }
}
