package com.masker.easynet.exception;

/**
 * Author: masker.
 * Date: 2017/3/14.
 * Description :
 */

public class EasyNetException extends RuntimeException {
    private String msg;
    public EasyNetException(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
}
