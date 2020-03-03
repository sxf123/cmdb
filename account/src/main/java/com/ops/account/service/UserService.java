package com.ops.account.service;

import com.ops.account.entity.User;
import com.ops.account.mapper.UserMapper;
import com.ops.account.request.UserRequest;
import com.ops.commons.enums.ErrorCodeAndMsg;
import com.ops.commons.exception.BaseException;
import com.ops.commons.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public Response userInfo(String userName){
        User user = userMapper.selectByUserName(userName);
        if(user == null){
            throw new BaseException(ErrorCodeAndMsg.User_does_not_exist);
        }
        return new Response(user);
    }

    public Response saveUser(UserRequest userRequest){
        if(userMapper.selectByUserName(userRequest.getUserName()) != null){
            throw new BaseException(ErrorCodeAndMsg.User_already_exist);
        }
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setPassWord(userRequest.getPassWord());
        user.setRealName(userRequest.getRealName());
        userMapper.saveUser(user);
        return Response.successResponse("添加用户成功");
    }
}
