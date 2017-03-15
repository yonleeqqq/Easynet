package com.masker.easynet.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;


/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : 创建Request
 */

public class RequestHelper {
    public static Request createGetRequest(String url) {
        return createGetRequest(url, null);
    }

    public static Request createGetRequest(String url, RequestParams params) {
        return createGetRequest(url,params,null);
    }

    /**
     * 根据参数创建一个get的Request
     * @param url 请求的url
     * @param params 请求的参数
     * @param headers 请求头
     * @return
     */
    public static Request createGetRequest(String url,RequestParams params,RequestParams headers){
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        //构造完整的url
        if(params != null){
            for(Map.Entry<String,String> entry:params.urlParams.entrySet()){
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        //添加Builder
        Headers.Builder headerBuilder = new Headers.Builder();
        if(headers != null){
            for(Map.Entry<String,String> entry:headers.urlParams.entrySet()){
                headerBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        return new Request.Builder()
                .url(urlBuilder.substring(0,urlBuilder.length()-1))
                .headers(headerBuilder.build())
                .get()
                .build();
    }


    public static Request createPostRequest(String url,RequestParams params){
        return createGetRequest(url,null,null);
    }

    /**
     * 构造普通的ost请求的Request
     * @param url 请求的url
     * @param params 请求的参数
     * @param headers 请求头
     * @return
     */
    public static Request createPostRequest(String url,RequestParams params,RequestParams headers){
        FormBody.Builder formBuilder = new FormBody.Builder();
        if(params != null){
            for(Map.Entry<String,String> entry:params.urlParams.entrySet()){
                formBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        Headers.Builder headerBuilder = new Headers.Builder();
        if(headers != null){
            for(Map.Entry<String,String> entry:params.urlParams.entrySet()){
                headerBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        return new Request.Builder()
                .url(url)
                .post(formBuilder.build())
                .headers(headerBuilder.build())
                .build();
    }

}
