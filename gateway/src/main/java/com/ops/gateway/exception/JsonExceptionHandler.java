package com.ops.gateway.exception;

import com.ops.commons.enums.ErrorCodeAndMsg;
import com.ops.commons.exception.BaseException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonExceptionHandler implements ErrorWebExceptionHandler {
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        this.messageReaders = messageReaders;
    }

    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        this.messageWriters = messageWriters;
    }

    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    private List<ViewResolver> viewResolvers = Collections.emptyList();

    private ThreadLocal<Map<String,Object>> exceptionHandlerResult = new ThreadLocal<>();

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable ex){
        String code = null;
        String msg = null;

        if(ex instanceof NotFoundException){
            code = Integer.toString(HttpStatus.NOT_FOUND.value());
            msg = ((NotFoundException) ex).getMessage();
        }else if(ex instanceof BaseException){
            BaseException baseException = (BaseException) ex;
            code = baseException.getResponse().getCode();
            msg = baseException.getResponse().getMsg();
        }else{
            code = ErrorCodeAndMsg.Network_error.getCode();
            msg = ErrorCodeAndMsg.Network_error.getMsg();
        }

        Map<String,Object> body = new HashMap<>(2,1);
        body.put("code",code);
        body.put("msg",msg);

        Map<String,Object> result = new HashMap<>(2,1);
        result.put("status",200);
        result.put("body",body);



        if (serverWebExchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        exceptionHandlerResult.set(result);
        ServerRequest serverRequest = ServerRequest.create(serverWebExchange,this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(),this::renderErrorResponse).route(serverRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(serverRequest))
                .flatMap((response) -> write(serverWebExchange,response));
    }

    protected Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest){
        Map<String,Object> result = exceptionHandlerResult.get();
        return ServerResponse.status((Integer) result.get("status"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(result.get("body")));
    }

    private Mono<? extends Void> write(ServerWebExchange serverWebExchange,
                                       ServerResponse serverResponse) {
        serverWebExchange.getResponse().getHeaders()
                .setContentType(serverResponse.headers().getContentType());
        return serverResponse.writeTo(serverWebExchange, new ResponseContext());
    }

    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return JsonExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return JsonExceptionHandler.this.viewResolvers;
        }

    }


}
