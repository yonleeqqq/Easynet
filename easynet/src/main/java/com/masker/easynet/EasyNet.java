package com.masker.easynet;

import com.masker.easynet.cookie.SimpleCookieJar;
import com.masker.easynet.exception.EasyNetException;
import com.masker.easynet.request.RequestCall;
import com.masker.easynet.request.RequestHelper;
import com.masker.easynet.request.RequestParams;


import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : 最基本的工具类，用于发送请求
 */

public class EasyNet {
    private static final long TIME_OUT = 30L;
    private static OkHttpClient mOkHttpClient;
    // 静态初始化块构造默认的OkHttpClient
    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //默认接受所有域名
                return true;
            }
        });
        //Cookie
        builder.cookieJar(new SimpleCookieJar());
        //Time_out
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT,TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT,TimeUnit.SECONDS);
        builder.followRedirects(true);

        mOkHttpClient = builder.build();
    }

    /**
     * 重新设置OkHttpClient
     * @param okHttpClient
     */
    public static void setOkHttpClient(OkHttpClient okHttpClient){
        if(okHttpClient == null){
            throw new EasyNetException("okHttpClient can't be null");
        }
        mOkHttpClient = okHttpClient;
    }

    public static RequestCall get(String url){
        return new RequestCall(mOkHttpClient.newCall(RequestHelper.createGetRequest(url)));
    }

    public static RequestCall get(String url, RequestParams params){
        return new RequestCall(mOkHttpClient.newCall(RequestHelper.createGetRequest(url,params)));
    }

    /**
     * 创建一个RequestCall 对象
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static RequestCall get(String url,RequestParams params,RequestParams headers){
        return new RequestCall(mOkHttpClient.newCall(RequestHelper.createGetRequest(url,params,headers)));
    }

    public static RequestCall post(String url,RequestParams params){
        return new RequestCall(mOkHttpClient.newCall(RequestHelper.createPostRequest(url,params)));
    }

    public static RequestCall post(String url,RequestParams params,RequestParams headers){
        return new RequestCall(mOkHttpClient.newCall(RequestHelper.createPostRequest(url,params,headers)));
    }

    private EasyNet(){
    }

}
