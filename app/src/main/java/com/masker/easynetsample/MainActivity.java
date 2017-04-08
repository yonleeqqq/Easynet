package com.masker.easynetsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.masker.easynet.EasyNet;
import com.masker.easynet.callback.Callback;
import com.masker.easynet.converter.GsonConverterFactory;
import com.masker.easynet.response.Response;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private ImageView mImageView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text_view);
        mImageView = (ImageView) findViewById(R.id.image_view);

        EasyNet net = new EasyNet.Builder()
                .setConverterFactory(GsonConverterFactory.create())
                .build();
        net.get().url("http://gank.io/api/data/Android/10/1")
                .build().execute(new Callback<Bean>(){

            @Override
            public void onSuccess(Response<Bean> response) {
                mTextView.setText(response.data.getResults().get(0).getDesc());
            }

            @Override
            public void onError(Call call, IOException e) {

            }
        });
    }
}
