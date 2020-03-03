package com.ops.host.controller;

import com.ops.host.request.HostRequest;
import com.ops.commons.response.Response;
import com.ops.host.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class HostController {

    @Autowired
    private HostService hostService;

    @PostMapping("/saveHost")
    public Response saveHost(@RequestBody HostRequest hostRequest){
        return hostService.saveHost(hostRequest);
    }

    @PostMapping("/findByHostName")
    public Response findByHostName(@RequestBody HostRequest hostRequest){
        return hostService.selectHostByHostName(hostRequest);
    }

    @PostMapping("/updateByHostName")
    public Response updateByHostName(@RequestBody HostRequest hostRequest){
        return hostService.updateByHostName(hostRequest);
    }

    @PostMapping("/deleteByHostName")
    public Response deleteByHostName(@RequestBody HostRequest hostRequest){
        return hostService.deleteByHostName(hostRequest);
    }

    @GetMapping("/findAllHost")
    public Response findAllHost(@RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="1")int pageSize){
        return hostService.findAllHost(pageNo,pageSize);
    }
}
