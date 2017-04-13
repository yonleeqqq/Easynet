package com.masker.easynetsample;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.masker.easynet.EasyNet;
import com.masker.easynet.callback.Callback;
import com.masker.easynet.callback.FileCallback;
import com.masker.easynet.converter.BitmapConverterFactory;
import com.masker.easynet.converter.GsonConverterFactory;
import com.masker.easynet.request.EasyRequestBody;
import com.masker.easynet.response.Response;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FileActivity extends AppCompatActivity {
    private static final String TAG = "FileActivity";

    private Button btnDownload;
    private Button btnUpload;
    private ProgressBar progressBar;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initViews();

        final EasyNet net = new EasyNet.Builder()
                //.setClient(new OkHttpClient())
                .addConverterFactory(BitmapConverterFactory.create()) //添加图片解析
                .addConverterFactory(GsonConverterFactory.create()) //添加实体类解析
                .build();
        final File file = new File(Environment.getExternalStorageDirectory(),"qmys.mp3");
        final String path = file.getAbsolutePath();
        final String url = "http://yinyueshiting.baidu.com/data2/music/37820715/23572524840032.m4a?xcode=64062ad2385f439196a2d3f13bf86a03";
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                net.get().url(url)
                        .build()
                        .execute(new FileCallback(path) {
                            @Override
                            public void onUpdateProgress(long curLength, long totalLength) {
                                int progress = (int)(curLength*100L/totalLength);
                                progressBar.setProgress(progress);
                            }

                            @Override
                            public void onSuccess(Response<File> response) {

                            }

                            @Override
                            public void onError(Call call, IOException e) {

                            }
                        });
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uploadUrl = "http://api.nohttp.net/upload";
                File upFile = new File(Environment.getExternalStorageDirectory(),"qingting.apk");
                net.upload()
                        .url(uploadUrl)
                        .file(file,"music")
                        .file(upFile,"apk")
                        .listener(new EasyRequestBody.UpLoadListener() {
                            @Override
                            public void onUpdateProgress(long curLength, long totalLength) {
                                int progress = (int)(curLength*100L/totalLength);
                                progressBar.setProgress(progress);
                                tvProgress.setText(" "+(curLength/1024)+"/"+(totalLength/1024)+"Kb");
                            }
                        })
                        .build()
                        .execute(new Callback<String>(){
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.i(TAG, "onSuccess: "+response.data);
                            }

                            @Override
                            public void onError(Call call, IOException e) {

                            }
                        });

            }
        });

    }
    private void initViews(){
        btnDownload = (Button) findViewById(R.id.btn_download);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
    }
}
