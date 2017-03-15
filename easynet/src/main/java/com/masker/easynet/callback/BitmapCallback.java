package com.masker.easynet.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Author: masker.
 * Date: 2017/3/15.
 * Description : 图片下载回调，没有做任何处理，只能用于简单的加载图片
 */

public abstract class BitmapCallback extends Callback<Bitmap>{
    @Override
    public Bitmap handleResponse(Call call, Response response) {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }
}
