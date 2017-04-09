package com.masker.easynet;

import com.masker.easynet.converter.Converter;
import com.masker.easynet.converter.StringConvertFactory;
import com.masker.easynet.exception.EasyNetException;
import com.masker.easynet.request.GetRequestBuilder;
import com.masker.easynet.request.PostFormRequestBuilder;


import okhttp3.OkHttpClient;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : base class for building request
 */

public class EasyNet {



    private InitParams mParams;


    private EasyNet(){

    }

    private void setmParams(InitParams p){
        mParams = p;
    }

    public GetRequestBuilder get(){
        return new GetRequestBuilder(mParams.mOkHttpClient,mParams.mConverterFactory);
    }

    public PostFormRequestBuilder post(){
        return new PostFormRequestBuilder(mParams.mOkHttpClient,mParams.mConverterFactory);
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
                mConverterFactory = StringConvertFactory.create();
            }
            easyNet.setmParams(this);
        }
    }

}
