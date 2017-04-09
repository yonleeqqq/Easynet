package com.masker.easynet.request;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.masker.easynet.converter.Converter;
import com.masker.easynet.exception.EasyNetException;

import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Author: masker.
 * Date: 2017/4/9.
 * Description :
 */

public class PostFormRequestBuilder extends RequestBuilder<PostFormRequestBuilder>
        implements WithParams{

    public PostFormRequestBuilder(OkHttpClient client, Converter.Factory factory) {
        super(client, factory);
    }

    @Override
    public HttpCall build() {
        Request.Builder builder = new Request.Builder();
        FormBody.Builder formBuilder = new FormBody.Builder();
        if(params != null){
            for (Map.Entry<String,String> param:params.entrySet()) {
                formBuilder.add(param.getKey(), param.getValue());
            }
        }
        Request request = builder.url(url).post(formBuilder.build()).tag(tag).build();
        Call call = mClient.newCall(request);
        return new HttpCall(call,mFactory);
    }

    @Override
    public PostFormRequestBuilder params(Map<String, String> params) {
        if(params == null){
            throw new EasyNetException("params is empty");
        }
        this.params = params;
        return this;
    }

    @Override
    public PostFormRequestBuilder addParam(String name, String value) {
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
