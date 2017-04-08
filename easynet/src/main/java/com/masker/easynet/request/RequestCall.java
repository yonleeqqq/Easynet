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
 * Description : 封装Call，并且将执行的结果通过handler分发到主线程，交给自定义的Callback去处理
 */

public class RequestCall<T> {
    private static final String TAG = "RequestCall";

    private Call mCall;
    private Handler mHander;
    private Converter.Factory mConvertFactroy;
    private Converter<okhttp3.Response,T> mConverter;

    private boolean isCalled = false;

    RequestCall(Call call,Converter.Factory converterFactory){
        mCall = call;
        mConvertFactroy = converterFactory;
        mHander = new Handler(Looper.getMainLooper());
    }

    /**
     *  异步请求
     * @param callback 回调方法，将会在主线程中得到执行
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
