package com.masker.easynet.converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.masker.easynet.exception.ConvertException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Author: masker.
 * Date: 2017/4/9.
 * Description :convert the response to bitmap
 */

public class BitmapConverterFactory extends Converter.Factory {
    private BitmapConverterFactory(){
    }

    public static BitmapConverterFactory create(){
        return new BitmapConverterFactory();
    }

    @Override
    public Converter<Response, Bitmap> createResponseConverter(Type type) throws ConvertException {
       if(type != Bitmap.class){
           throw new ConvertException("response must be bitmap");
       }
       return new BitmapConverter();
    }

    final class BitmapConverter implements Converter<Response,Bitmap>{

        @Override
        public Bitmap convert(Response src) throws ConvertException {
            Bitmap result = null;
            result = BitmapFactory.decodeStream(src.body().byteStream());
            if(result == null){
                throw new ConvertException("convert failed!");
            }
            return result;
        }
    }

}
