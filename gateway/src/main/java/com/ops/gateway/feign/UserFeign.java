package com.ops.gateway.feign;

import com.ops.commons.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("account-service")
public interface UserFeign {
    @GetMapping("/userInfo")
    Response userInfo(@RequestParam String userName);
}
