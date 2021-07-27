package com.example.staybuddy.helpers;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {

    // Initialized base url over here
    private static final String BASE_URL = "http://androidbackend-env.eba-pmwntcz3.us-east-2.elasticbeanstalk.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static SyncHttpClient clientSync = new SyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.post(null, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    public static void postSync(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        clientSync.post(null, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}