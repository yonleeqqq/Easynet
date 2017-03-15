package com.masker.easynet.cookie;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : 可持久化存储的CookieJar
 */

public class PersistentCookieJar implements CookieJar{

    private final PersistentCookieStore cookieStore;

    public PersistentCookieJar(PersistentCookieStore cookieStore){
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.add(url,cookies);
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
