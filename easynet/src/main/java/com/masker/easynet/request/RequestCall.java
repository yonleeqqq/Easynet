package com.masker.easynet.request;

import android.os.Handler;
import android.os.Looper;

import com.masker.easynet.EasyNet;
import com.masker.easynet.callback.Callback;
import com.masker.easynet.exception.EasyNetException;

import java.io.IOException;
import java.io.ObjectInput;


import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : 封装Call，并且将执行的结果通过handler分发到主线程，交给自定义的Callback去处理
 */

public class RequestCall {
    private Call mCall;
    private Handler mHander;

    private boolean isCalled = false;

    public RequestCall(Call call){
        mCall = call;
        mHander = new Handler(Looper.getMainLooper());
    }

    /**
     *  异步请求
     * @param callback 回调方法，将会在主线程中得到执行
     */
    public void execute(final Callback callback){
        if(isCalled == true){
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
            public void onResponse(final Call call, final Response response) throws IOException {
                final Object o = callback.handleResponse(call,response);
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(o);
                    }
                });
            }
        });
    }

    /**
     * 同步请求，不能再主线程被调用，会阻塞线程
     * @return 请求的Response
     */
    public Response synExecute(){
        if(isCalled == true){
            throw new EasyNetException("The call has been called!");
        }
        isCalled = true;
        Response result = null;
        try {
            result = mCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
