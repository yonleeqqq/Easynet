package com.masker.easynet.request;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.masker.easynet.EasyNet;
import com.masker.easynet.callback.Callback;
import com.masker.easynet.converter.Converter;
import com.masker.easynet.converter.GsonConverterFactory;
import com.masker.easynet.exception.ConvertException;
import com.masker.easynet.exception.EasyNetException;
import com.masker.easynet.response.Response;

import java.io.IOException;
import java.io.ObjectInput;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


import okhttp3.Call;



/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : convert response and distribute it to main thread
 */

public class HttpCall<T> {
    private static final String TAG = "HttpCall";

    private Call mCall;
    private Handler mHander;
    private List<Converter.Factory> mFactories;
    private Converter<okhttp3.Response,T> mConverter;

    private boolean isCalled = false;

    HttpCall(Call call, List<Converter.Factory> factories){
        mCall = call;
        mFactories = factories;
        mHander = new Handler(Looper.getMainLooper());
    }

    /**
     *  Async requst
     * @param callback callback,runing on main thread
     */
    public void execute(final Callback<T> callback){
        if(isCalled){
            throw new EasyNetException("The call has been called!");
        }
        isCalled = true;
        mCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                       callback.onError(call,e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final okhttp3.Response response) throws IOException {
                T body = null;
                if(callback.isHandleResponse()){
                    Log.i(TAG, "onResponse: "+"handle Response");
                    body = callback.handleResponse(response);
                }
                else{
                    body = handleResponse(callback,response);
                }
                final Response<T> res = new Response<>();
                res.data = body;
                res.setHeaders(response.headers());
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(res);
                    }
                });
            }
        });
    }

    /*
     * cancel the call
     */
    public void cancel(){
        if(!mCall.isCanceled()){
            mCall.cancel();
        }
    }

    public T handleResponse(Callback<T> callback, okhttp3.Response response){
        T body = null;
        Type type = callback.getClass().getGenericSuperclass();
        Type realType = Object.class;
        if(type instanceof ParameterizedType){
            realType = ((ParameterizedType)type).getActualTypeArguments()[0];
        }
        boolean finish = false;
        int index = 0;
        while(!finish){
            try {
                mConverter = (Converter<okhttp3.Response, T>) mFactories.get(index).createResponseConverter(realType);
                body = mConverter.convert(response);
                finish = true;
            } catch (ConvertException e) {
                Log.i(TAG, "onResponse: "+"convert failed");
                index++;
                if(index == mFactories.size()){
                    throw new EasyNetException("converte response failed!");
                }
            }
        }
        return body;
    }
}
