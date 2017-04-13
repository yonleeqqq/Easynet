package com.masker.easynet.request;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import java.io.IOException;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;


/**
 * Author: masker.
 * Date: 2017/4/12.
 * Description :
 */

public class EasyRequestBody extends RequestBody{
    private static final String TAG = "EasyRequestBody";

    private RequestBody delegate;
    private UpLoadListener listener;
    private Handler mHandler;

    public EasyRequestBody(RequestBody requestBody){
        this.delegate = requestBody;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void setUpLoadListener(UpLoadListener listener){
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength(){
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        delegate.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink{
        private long curLength;
        private long totalLength = contentLength();

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            curLength += byteCount;
            if(listener != null){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onUpdateProgress(curLength,totalLength);
                    }
                });
            }
        }
    }

    public interface UpLoadListener{
        void onUpdateProgress(long curLength, long totalLength);
    }
}
