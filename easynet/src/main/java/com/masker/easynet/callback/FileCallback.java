package com.masker.easynet.callback;

import android.icu.text.DateFormat;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.masker.easynet.response.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;

/**
 * Author: masker.
 * Date: 2017/4/12.
 * Description :
 */

public abstract class FileCallback extends Callback<File>{
    private static final String TAG = "FileCallback";

    private String pathName;
    private Handler mHandler;
    public FileCallback(String pathName){
        this.pathName = pathName;
        mHandler = new Handler(Looper.getMainLooper());
        handleResponse = true;
    }

    @Override
    public File handleResponse(okhttp3.Response response) {
        File file = new File(pathName);
        if(file.exists()){
            file.delete();
        }
        try (InputStream is = response.body().byteStream();
             FileOutputStream fos = new FileOutputStream(file)){
            final long totalLength = response.body().contentLength();
            byte[] buffer = new byte[1024*10];
            long curLength = 0;
            int len= 0;
            while((len = is.read(buffer)) > 0){
                fos.write(buffer,0,len);
                curLength += len;
                final long finalCurLength = curLength;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onUpdateProgress(totalLength, finalCurLength);
                    }
                });
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    /*
    * running on ui thread
     */
    public abstract void onUpdateProgress(long totalLength,long curLength);
}
