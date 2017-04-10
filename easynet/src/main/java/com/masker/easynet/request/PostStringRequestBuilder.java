package com.masker.easynet.request;

import com.masker.easynet.converter.Converter;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author: masker.
 * Date: 2017/4/10.
 * Description :post a string to server
 */

public class PostStringRequestBuilder extends RequestBuilder<PostStringRequestBuilder>{
    private MediaType mediaType;
    private String content;

    public PostStringRequestBuilder(OkHttpClient client, List<Converter.Factory> factories,
                                    MediaType mediaType,String content) {
        super(client, factories);
        if(mediaType == null){
            throw new IllegalArgumentException("mediaType == null");
        }
        this.mediaType = mediaType;
        if(content == null){
            throw new IllegalArgumentException("string content == null");
        }
        this.content = content;
    }

    @Override
    public HttpCall build() {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if(headers != null){
            for (Map.Entry<String,String> header:headers.entrySet()){
                builder.header(header.getKey(),header.getValue());
            }
        }
        if(tag != null){
            builder.tag(tag);
        }
        RequestBody requestBody = RequestBody.create(mediaType,content);
        Request request = builder.post(requestBody).build();
        Call call = mClient.newCall(request);
        return new HttpCall(call,mFactories);
    }
}
