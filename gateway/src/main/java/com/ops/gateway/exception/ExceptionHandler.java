package com.ops.gateway.exception;

import com.ops.commons.exception.BaseException;
import com.ops.commons.response.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Component
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
    @ResponseBody
    public Response handleBaseException(BaseException ex){
        Response response;
        response = new Response(ex.getResponse().getCode(),ex.getResponse().getMsg());
        return response;
    }
}
