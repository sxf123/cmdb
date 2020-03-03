package com.ops.gateway.controller;

import com.ops.commons.response.Response;
import com.ops.gateway.feign.UserFeign;
import com.ops.gateway.request.UserRequest;
import com.ops.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserFeign userFeign;

    @PostMapping("/login")
    public Response login(@RequestBody UserRequest userRequest){
        return userService.userLogin(userRequest);
    }
}
