package com.masker.easynet.exception;

/**
 * Author: masker.
 * Date: 2017/4/9.
 * Description :convert exception
 */

public class ConvertException extends Exception{
    private String msg;

    public ConvertException(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
}
