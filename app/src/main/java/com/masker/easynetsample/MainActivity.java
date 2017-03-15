package com.masker.easynetsample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.masker.easynet.EasyNet;
import com.masker.easynet.callback.BitmapCallback;
import com.masker.easynet.callback.StringCallback;

import java.io.IOException;

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
        EasyNet.get("https://octodex.github.com/images/mcefeeline.jpg")
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap response) {
                        mImageView.setImageBitmap(response);
                    }

                    @Override
                    public void onError(Call call, IOException e) {

                    }
                });
    }
}
