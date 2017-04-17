package com.masker.easynet;

import com.masker.easynet.converter.Converter;
import com.masker.easynet.converter.StringConvertFactory;
import com.masker.easynet.exception.EasyNetException;
import com.masker.easynet.request.GetRequestBuilder;
import com.masker.easynet.request.PostFileRequestBuilder;
import com.masker.easynet.request.PostFormRequestBuilder;
import com.masker.easynet.request.PostStringRequestBuilder;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : base class for building request
 */

public class EasyNet {

    private static OkHttpClient deafultClient;

    public static void setDeafultClient(OkHttpClient client){
        deafultClient = client;
    }

    private InitParams mParams;


    private EasyNet(){

    }

    private void setmParams(InitParams p){
        mParams = p;
    }

    public GetRequestBuilder get(){
        return new GetRequestBuilder(mParams.mOkHttpClient,mParams.mConverterFactories);
    }

    /*
     * post form
     */
    public PostFormRequestBuilder post(){
        return new PostFormRequestBuilder(mParams.mOkHttpClient,mParams.mConverterFactories);
    }

    /*
    * post a string,default mediatype text/plain
     */
    public PostStringRequestBuilder post(String content){
        MediaType type = MediaType.parse("text/plain; charset=utf-8");
        return new PostStringRequestBuilder(mParams.mOkHttpClient,mParams.mConverterFactories,
                type,content);
    }

    public PostStringRequestBuilder post(String content,MediaType mediaType){
        return new PostStringRequestBuilder(mParams.mOkHttpClient,mParams.mConverterFactories,
                mediaType,content);
    }

    /*
     * upload files
     */
    public PostFileRequestBuilder upload(){
        return new PostFileRequestBuilder(mParams.mOkHttpClient,mParams.mConverterFactories);
    }

    public static class Builder{
        private InitParams P;

        public Builder(){
            P = new InitParams();
            //default converterfactory
            P.mConverterFactories = new ArrayList<>();
            P.mConverterFactories.add(StringConvertFactory.create());
        }

        public Builder setClient(OkHttpClient client){
            P.mOkHttpClient = client;
            return this;
        }

        public Builder addConverterFactory(Converter.Factory factory){
            if(factory == null){
                throw new IllegalArgumentException("factory == null");
            }
            P.mConverterFactories.add(factory);
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
        List<Converter.Factory> mConverterFactories;
        void apply(EasyNet easyNet){
            //default params;
            if(mOkHttpClient == null){
                if(deafultClient == null){
                    mOkHttpClient = new OkHttpClient();
                }
                else{
                    mOkHttpClient = deafultClient;
                }
            }
            easyNet.setmParams(this);
        }
    }
}
