package com.ops.account.exception;

import com.ops.commons.exception.BaseException;
import com.ops.commons.response.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
    public Response handBaseException(BaseException ex){
        Response response = new Response(ex.getResponse().getCode(),ex.getResponse().getMsg());
        return response;
    }
}
