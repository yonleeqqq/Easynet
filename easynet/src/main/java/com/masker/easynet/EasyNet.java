package com.masker.easynet;

import android.text.TextUtils;

import com.masker.easynet.converter.Converter;
import com.masker.easynet.exception.EasyNetException;
import com.masker.easynet.request.RequestCall;


import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : base class for building request
 */

public class EasyNet {

    private InitParams mParams;
    private Request.Builder mRequestBuilder;
    private Type type;

    private EasyNet(){
        mRequestBuilder = new Request.Builder();
    }

    private void setmParams(InitParams p){
        mParams = p;
    }

    public EasyNet get(){
        mRequestBuilder.get();
        return this;
    }
    public EasyNet head(){
        mRequestBuilder.head();
        return this;
    }

    public EasyNet url(String url){
        mRequestBuilder.url(url);
        return this;
    }
    public EasyNet url(URL url){
        mRequestBuilder.url(url);
        return this;
    }

    public EasyNet url(HttpUrl url){
        mRequestBuilder.url(url);
        return this;
    }

    /*
     * Set a field with the specified value. If the field is not found, it is added.
     */
    public EasyNet header(String name,String value){
        mRequestBuilder.header(name,value);
        return this;
    }

    /*
     * add headers
     */
    public EasyNet headers(Headers headers){
        mRequestBuilder.headers(headers);
        return this;
    }

    /*
     * add headers by using map
     */
    public EasyNet headers(Map<String,String> headers){
        for(Map.Entry<String,String> entry:headers.entrySet()){
            if( (!TextUtils.isEmpty(entry.getKey()) )
                    && (!TextUtils.isEmpty(entry.getValue())) ){
                header(entry.getKey(),entry.getValue());
            }
            else{
                throw new EasyNetException("name or key is empty");
            }
        }
        return this;
    }

    public EasyNet addHeader(String name,String value){
        mRequestBuilder.addHeader(name,value);
        return this;
    }

    public EasyNet removeHeader(String name){
        mRequestBuilder.removeHeader(name);
        return this;
    }

    public EasyNet setType(Type type){
        this.type = type;
        return this;
    }

    public RequestCall build(){
        return build(true);
    }

    public RequestCall build(boolean resetParams){
        Request request = mRequestBuilder.build();
        //reset request builder
        if(resetParams){
            mRequestBuilder = new Request.Builder();
        }
        Call okHttpCall = mParams.mOkHttpClient.newCall(request);
        if(type == null){
            type = Object.class;
        }
        RequestCall call = new RequestCall(okHttpCall,mParams.mConverterFactory,type);
        return call;
    }

    public static class Builder{
        private InitParams P;

        public Builder(){
            P = new InitParams();
        }

        public Builder setClient(OkHttpClient client){
            P.mOkHttpClient = client;
            return this;
        }

        public Builder setConverterFactory(Converter.Factory factory){
            P.mConverterFactory = factory;
            return this;
        }

        public EasyNet build(){
            EasyNet instance = new EasyNet();
            P.apply(instance);
            return instance;
        }
    }

    private static class InitParams{
        OkHttpClient mOkHttpClient;
        Converter.Factory mConverterFactory;
        void apply(EasyNet easyNet){
            //default params;
            if(mOkHttpClient == null){
                mOkHttpClient = new OkHttpClient();
            }
            if(mConverterFactory == null){
                throw new EasyNetException("converterfactory can't be null");
            }
            easyNet.setmParams(this);
        }
    }
}
