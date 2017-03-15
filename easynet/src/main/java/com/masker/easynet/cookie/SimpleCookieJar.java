package com.masker.easynet.cookie;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description :
 */

public class SimpleCookieJar implements CookieJar{
    private final ArrayMap<HttpUrl,List<Cookie>> cookieStore = new ArrayMap<>();

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url,cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        if(cookies == null){
            cookies = new ArrayList<>();
        }
        return cookies;
    }

}
