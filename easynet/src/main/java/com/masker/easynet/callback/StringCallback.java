package com.masker.easynet.callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Author: masker.
 * Date: 2017/3/15.
 * Description : 将Response转成String,然后进行下一步处理
 */

public abstract class StringCallback extends Callback<String>{

    @Override
    public String handleResponse(Call call,Response response) {
        if(!response.isSuccessful()){
            onError(call,new IOException("response error"));
        }
        String result = null;
        try {
            result = response.body().string();
            onSuccess(result);
        } catch (IOException e) {
            e.printStackTrace();
            onError(call,e);
        }
        return result;
    }
}
