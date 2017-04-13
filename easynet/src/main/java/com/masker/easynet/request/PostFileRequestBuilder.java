package com.masker.easynet.request;

import android.util.Log;

import com.masker.easynet.converter.Converter;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author: masker.
 * Date: 2017/4/12.
 * Description :
 */

public class PostFileRequestBuilder extends RequestBuilder<PostFileRequestBuilder> {
    private MediaType mediaType;
    private List<File> fileList;
    private List<String> nameList;
    private EasyRequestBody.UpLoadListener listener;

    public PostFileRequestBuilder(OkHttpClient client, List<Converter.Factory> factories) {
        super(client, factories);
        fileList = new ArrayList<>();
        nameList = new ArrayList<>();
    }

    public  PostFileRequestBuilder listener(EasyRequestBody.UpLoadListener listener){
        this.listener = listener;
        return this;
    }

    public PostFileRequestBuilder file(File file, String name){
        if(file == null || name == null){
            throw new IllegalArgumentException("file or name == null");
        }

        fileList.add(file);
        nameList.add(name);
        return this;
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
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        multipartBuilder.setType(MultipartBody.FORM);
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            String name = nameList.get(i);
            RequestBody requestBody = RequestBody.create(getMediaType(file),file);
            multipartBuilder.addFormDataPart(name,file.getName(),requestBody);
        }
        MultipartBody multipartBody = multipartBuilder.build();

        EasyRequestBody realBody = new EasyRequestBody(multipartBody);
        realBody.setUpLoadListener(listener);
        Request request = builder.post(realBody).build();
        Call call = mClient.newCall(request);
        return new HttpCall(call,mFactories);
    }

    private MediaType getMediaType(File file){
        String path = file.getPath();
        String contentType = null;
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        try{
            contentType = fileNameMap.getContentTypeFor(URLEncoder.encode(path,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(contentType == null){
            contentType = "application/octet-stream";
        }
        return MediaType.parse(contentType);
    }
}
