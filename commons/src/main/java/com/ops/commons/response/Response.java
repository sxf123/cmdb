package com.ops.commons.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ops.commons.enums.ErrorCodeAndMsg;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -4505655308965878999L;

    private static final String successCode = "0000";

    private T data;

    private String code;

    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Response(){
        this.code = successCode;
        this.msg = "请求成功";
    }

    public Response(String code, String msg){
        this();
        this.code = code;
        this.msg = msg;
    }
    public Response(String msg){
        this();
        this.msg = msg;
    }
    public Response(String code, String msg, T data){
        this();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Response(T data) {
        this();
        this.data = data;
    }

    public static Response successResponse(String msg){
        Response response = new Response(successCode,msg);
        return response;
    }

    public static Response errorResponse(ErrorCodeAndMsg errorCodeAndMsg){
        Response response = new Response(errorCodeAndMsg.getCode(),errorCodeAndMsg.getMsg());
        return response;
    }
}