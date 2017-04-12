package com.masker.easynetsample;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.masker.easynet.EasyNet;
import com.masker.easynet.callback.FileCallback;
import com.masker.easynet.converter.BitmapConverterFactory;
import com.masker.easynet.converter.GsonConverterFactory;
import com.masker.easynet.response.Response;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

public class FileActivity extends AppCompatActivity {

    private Button btnDownload;
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
        File file = new File(Environment.getExternalStorageDirectory(),"qingting.apk");
        final String path = file.getAbsolutePath();
        final String url = "http://qn-apk.wdjcdn.com/5/9c/cd0c77a36981112b2850290a787249c5.apk";
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                net.get().url(url)
                        .build()
                        .execute(new FileCallback(path) {
                            @Override
                            public void onUpdateProgress(long totalLength, long curLength) {
                                int progress = (int)(curLength*100L/totalLength);
                                progressBar.setProgress(progress);
                                tvProgress.setText(" "+(curLength/1024)+"/"+(totalLength/1024)+"Kb");
                            }

                            @Override
                            public void onSuccess(Response<File> response) {
                                Toast.makeText(FileActivity.this,"finish",Toast.LENGTH_SHORT).show();
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
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
    }
}
