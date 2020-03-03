package com.ops.host.controller;

import com.ops.commons.response.Response;
import com.ops.host.request.NodeRequest;
import com.ops.host.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    NodeService nodeService;

    @PostMapping("/saveNode")
    public Response saveNode(@RequestBody NodeRequest nodeRequest){
        return nodeService.saveNode(nodeRequest);
    }
}
