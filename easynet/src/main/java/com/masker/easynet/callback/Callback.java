package com.masker.easynet.callback;

import com.masker.easynet.converter.Converter;
import com.masker.easynet.response.Response;

import java.io.IOException;

import okhttp3.Call;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : base callback
 */

public interface Callback<T> {

    void onSuccess(Response<T> response);
    void onError(Call call,IOException e);

}
