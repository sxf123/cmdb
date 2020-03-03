package com.ops.account.controller;

import com.ops.account.request.UserRequest;
import com.ops.account.service.UserService;
import com.ops.commons.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/userInfo")
    public Response userInfo(@RequestParam String userName){
        return userService.userInfo(userName);
    }

    @PostMapping("/saveUser")
    public Response saveUser(@RequestBody UserRequest userRequest){
        return userService.saveUser(userRequest);
    }
}
