package com.masker.easynet.request;

import java.util.Map;

/**
 * Author: masker.
 * Date: 2017/4/8.
 * Description :
 */

public interface WithParams {
    RequestBuilder params(Map<String,String> params);
    RequestBuilder addParam(String name,String value);

}
