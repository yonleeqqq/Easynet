package com.masker.easynet.response;

import okhttp3.Headers;

/**
 * Author: masker.
 * Date: 2017/4/7.
 * Description :
 */

public class Response<T> {
    public T data;
    private Headers headers;

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Headers getHeaders() {
        return headers;
    }
}
