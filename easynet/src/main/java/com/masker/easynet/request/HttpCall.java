package com.masker.easynet.request;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.masker.easynet.EasyNet;
import com.masker.easynet.callback.Callback;
import com.masker.easynet.converter.Converter;
import com.masker.easynet.exception.EasyNetException;
import com.masker.easynet.response.Response;

import java.io.IOException;
import java.io.ObjectInput;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


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
    private Converter.Factory mConvertFactroy;
    private Converter<okhttp3.Response,T> mConverter;

    private boolean isCalled = false;

    HttpCall(Call call, Converter.Factory converterFactory){
        mCall = call;
        mConvertFactroy = converterFactory;
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
                Type type = callback.getClass().getGenericSuperclass();
                Type realType = ((ParameterizedType)type).getActualTypeArguments()[0];
                mConverter = (Converter<okhttp3.Response, T>) mConvertFactroy.createResponseConverter(realType);
                T body = mConverter.convert(response);
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


}
