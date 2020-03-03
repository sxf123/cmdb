package com.ops.host.exception;

import com.ops.commons.exception.BaseException;
import com.ops.commons.response.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
    @ResponseBody
    public Response handlerBaseException(HttpServletRequest httpServletRequest,BaseException ex){
        Response response;
        response = new Response(ex.getResponse().getCode(),ex.getResponse().getMsg());
        return response;
    }

}
