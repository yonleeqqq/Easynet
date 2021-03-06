package com.masker.easynet.converter;

import android.util.Log;

import com.masker.easynet.exception.ConvertException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;



/**
 * Author: masker.
 * Date: 2017/4/9.
 * Description : deafault convert factory,just get the reponse string
 */

public class StringConvertFactory extends Converter.Factory{


    private StringConvertFactory(){}

    public static StringConvertFactory create(){
        return new StringConvertFactory();
    }

    @Override
    public Converter<Response, String> createResponseConverter(Type type) throws ConvertException {
        if(type != String.class){
            throw new ConvertException("response type must be string");
        }

        return new StringResponseConverter();
    }


    final class StringResponseConverter implements Converter<Response,String>{

        @Override
        public String convert(Response src) throws ConvertException {
            String result = null;
            try {
                result = src.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ConvertException("io exception");
            }
            return result;
        }
    }
}
