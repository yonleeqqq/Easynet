package com.masker.easynet.callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description : 回调的基类
 */

public abstract class Callback<T> {
    public abstract void onSuccess(T response);
    public abstract void onError(Call call,IOException e);

    /**
     * 处理Response,转换成对应的泛型数据，需要注意的是，这个方法需要在子线程进行，因为读取
     * response是耗时操作，不可以在主线程进行。
     * @param call
     * @param response
     * @return
     */
    public abstract T handleResponse(Call call,Response response);
}
