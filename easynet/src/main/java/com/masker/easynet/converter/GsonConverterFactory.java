package com.masker.easynet.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Author: masker.
 * Date: 2017/4/7.
 * Description :
 */

public class GsonConverterFactory extends Converter.Factory{
    final Gson gson;
    private GsonConverterFactory(Gson gson){
        if(gson == null){
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    public static GsonConverterFactory create(){
        return create(new Gson());
    }

    public static GsonConverterFactory create(Gson gson){
        return new GsonConverterFactory(gson);
    }

    @Override
    public Converter<Response, ?> createResponseConverter(Type type) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter);
    }


    final class GsonResponseBodyConverter<T> implements Converter<Response, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override public T convert(Response value) throws IOException {
            JsonReader jsonReader = gson.newJsonReader(value.body().charStream());
            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }
}
