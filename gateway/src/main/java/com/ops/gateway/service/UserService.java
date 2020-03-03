package com.ops.gateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ops.commons.enums.ErrorCodeAndMsg;
import com.ops.commons.exception.BaseException;
import com.ops.commons.response.Response;
import com.ops.gateway.entity.User;
import com.ops.gateway.mapper.UserMapper;
import com.ops.gateway.request.TokenRequest;
import com.ops.gateway.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    public Response userLogin(UserRequest userRequest){
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        User user = userMapper.selectByUserName(userRequest.getUserName());
        if(user == null){
            throw new BaseException(ErrorCodeAndMsg.User_does_not_exist);
        }else if(!userRequest.getPassWord().equals(user.getPassWord())){
            throw new BaseException(ErrorCodeAndMsg.User_password_error);
        }
        try {
            String jsonWebToken = JWT.create().withAudience(user.getUserName()).withExpiresAt(date).sign(Algorithm.HMAC256(user.getPassWord()));
            TokenRequest tokenRequest = new TokenRequest();
            tokenRequest.setToken(jsonWebToken);
            return new Response(tokenRequest);
        }catch (Exception e){
            if(e instanceof BaseException){
                throw e;
            }else{
                throw new BaseException(ErrorCodeAndMsg.Network_error);
            }
        }
    }
}
