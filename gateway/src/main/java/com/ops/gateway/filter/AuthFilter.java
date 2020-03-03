package com.ops.gateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ops.commons.enums.ErrorCodeAndMsg;
import com.ops.commons.exception.BaseException;
import com.ops.commons.response.Response;
import com.ops.gateway.entity.User;
import com.ops.gateway.feign.UserFeign;
import com.ops.gateway.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Value("#{'${exclude_urls}'.split(',')}")
    public List<String> excludeList;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserFeign userFeign;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain chain){
        String requestUri = serverWebExchange.getRequest().getURI().getPath();
        boolean status = excludeList.contains(requestUri);
        if(!status){
            String token = serverWebExchange.getRequest().getHeaders().getFirst("Authorization");
            if(StringUtils.isBlank(token)){
                throw new BaseException(ErrorCodeAndMsg.Token_is_null);
            }
            String tokenUserName = JWT.decode(token).getAudience().get(0);
            Response response = userFeign.userInfo(tokenUserName);
            User user = (User)response.getData();
            if(user == null){
                throw new BaseException(ErrorCodeAndMsg.Token_is_error);
            }
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassWord())).build();
            try{
                jwtVerifier.verify(token);
            }catch (Exception ex){
                if(ex instanceof BaseException){
                    throw ex;
                }else if(ex instanceof TokenExpiredException){
                    throw new BaseException(ErrorCodeAndMsg.Token_is_expired);
                }else if(ex instanceof JWTVerificationException){
                    throw new BaseException(ErrorCodeAndMsg.Token_is_error);
                }else{
                    ex.printStackTrace();
                }
            }
        }
        return chain.filter(serverWebExchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
