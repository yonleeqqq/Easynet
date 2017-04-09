package com.masker.easynet.request;

import android.text.TextUtils;
import android.util.ArrayMap;


import com.masker.easynet.converter.Converter;
import com.masker.easynet.exception.EasyNetException;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Author: masker.
 * Date: 2017/4/8.
 * Description : base request builder
 */

public abstract class RequestBuilder<T extends RequestBuilder> {

    protected OkHttpClient mClient;
    protected List<Converter.Factory> mFactories;

    protected String url;
    protected Object tag;
    protected Map<String,String> headers;
    protected Map<String,String> params;

     RequestBuilder(OkHttpClient client, List<Converter.Factory> factories){
        mClient = client;
        mFactories = factories;
    }

    /*
     * set url
     */
    public T url(String url){
        if(TextUtils.isEmpty(url)){
            throw new EasyNetException("url is empty");
        }
        this.url = url;
        return (T) this;
    }

    /*
     * set tag
     */
    public T tag(Object tag){
        if(tag == null){
            throw new EasyNetException("tag is null");
        }
        this.tag = tag;
        return (T) this;
    }

    /*
     * set headers
     */
    public T headers(Map<String,String> headers){
        this.headers = headers;
        return (T) this;
    }

    /*
     * add a header.
     */
    public T addHeader(String name,String value){
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(value)){
            throw new EasyNetException("header name or value is empty");
        }
        if(headers == null){
            headers = new ArrayMap<>(5);
        }
        headers.put(name,value);
        return (T) this;
    }

    /*
     * remove header
     */
    public T removeHeader(String name){
        if(headers != null){
            headers.remove(name);
        }
        return (T) this;
    }


    public abstract HttpCall build();

}
