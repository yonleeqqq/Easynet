package com.masker.easynet.converter;

import com.masker.easynet.exception.ConvertException;
import com.masker.easynet.response.Response;

import java.io.IOException;
import java.lang.reflect.Type;



/**
 * Author: masker.
 * Date: 2017/4/6.
 * Description : convert src to des
 */

public interface  Converter<S,D> {
     D convert(S src) throws ConvertException;
     public abstract class Factory{
          public Converter<okhttp3.Response,?> createResponseConverter(Type type) throws ConvertException {
               return null;
          }
     }
}
