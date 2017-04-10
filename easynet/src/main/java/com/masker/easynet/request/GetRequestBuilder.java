package com.masker.easynet.request;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.masker.easynet.converter.Converter;
import com.masker.easynet.exception.EasyNetException;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Author: masker.
 * Date: 2017/4/8.
 * Description :
 */

public class GetRequestBuilder extends RequestBuilder<GetRequestBuilder>
        implements WithParams{


    public GetRequestBuilder(OkHttpClient client, List<Converter.Factory> factories) {
        super(client, factories);
    }

    @Override
    public HttpCall build() {
        Request.Builder builder = new Request.Builder();
        //handle params
        resetUrl();
        builder.url(url);
        if(headers != null){
            for (Map.Entry<String,String> header:headers.entrySet()){
                builder.header(header.getKey(),header.getValue());
            }
        }
        if(tag != null){
            builder.tag(tag);
        }
        Request request = builder.build();
        Call call = mClient.newCall(request);
        return new HttpCall(call, mFactories);
    }

    /*
     * add the param to the url
     */
    private void resetUrl(){
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        if(params != null){
            for (Map.Entry<String,String> param:params.entrySet()){
                String str = param.getKey()+"="+param.getValue()+"&";
                sb.append(str.trim());
            }
        }
        //delete the last char,maybe ? or &
        sb.deleteCharAt(sb.length()-1);
    }

    @Override
    public GetRequestBuilder params(Map<String, String> params) {
        if(params == null){
            throw new EasyNetException("params is empty");
        }
        this.params = params;
        return this;
    }

    @Override
    public GetRequestBuilder addParam(String name, String value) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(value)){
            throw new EasyNetException("param is empty");
        }
        if(params == null){
            params = new ArrayMap<>();
        }
        params.put(name,value);
        return this;
    }
}
