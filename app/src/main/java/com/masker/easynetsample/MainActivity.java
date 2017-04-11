package com.masker.easynetsample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
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
import okhttp3.OkHttpClient;

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

        EasyNet net = new EasyNet.Builder()
                //.setClient(new OkHttpClient())
                .addConverterFactory(BitmapConverterFactory.create()) //添加图片解析
                .addConverterFactory(GsonConverterFactory.create()) //添加实体类解析
                .build();
        

    }

    private void initView(){
        btnGet = (Button) findViewById(R.id.btn_get);
        btnPost = (Button) findViewById(R.id.btn_post);
        btnPostString = (Button) findViewById(R.id.btn_post_string);
        tvResult = (TextView) findViewById(R.id.tv_result);
        ivResult = (ImageView) findViewById(R.id.iv_result);
    }
}
