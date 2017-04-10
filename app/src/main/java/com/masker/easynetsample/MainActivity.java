package com.masker.easynetsample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.masker.easynet.EasyNet;
import com.masker.easynet.callback.Callback;
import com.masker.easynet.converter.BitmapConverterFactory;
import com.masker.easynet.converter.GsonConverterFactory;
import com.masker.easynet.converter.StringConvertFactory;
import com.masker.easynet.response.Response;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private Button btnGet;
    private Button btnPost;
    private Button btnPostString;
    private ImageView ivResult;
    private TextView tvResult;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        final EasyNet net = new EasyNet.Builder()
                .addConverterFactory(BitmapConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://api.nohttp.net/upload";
            }
        });

        btnPostString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://api.nohttp.net/postBody";
                net.post("this is a string")
                        .url(url)
                        .build()
                        .execute(new Callback<String>(){
                            @Override
                            public void onSuccess(Response<String> response) {
                                tvResult.setText(response.data);
                            }

                            @Override
                            public void onError(Call call, IOException e) {

                            }
                        });

            }
        });

    }

    private void initView(){
        btnGet = (Button) findViewById(R.id.btn_get);
        btnPost = (Button) findViewById(R.id.btn_post);
        btnPostString = (Button) findViewById(R.id.btn_post_string);
        tvResult = (TextView) findViewById(R.id.tv_result);
        ivResult = (ImageView) findViewById(R.id.iv_result);
    }
}
