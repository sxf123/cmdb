package com.ops.host.service;

import com.ops.commons.enums.ErrorCodeAndMsg;
import com.ops.commons.exception.BaseException;
import com.ops.commons.response.Response;
import com.ops.host.entity.Node;
import com.ops.host.mapper.NodeMapper;
import com.ops.host.request.NodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeService {

    @Autowired
    NodeMapper nodeMapper;

    public Response saveNode(NodeRequest nodeRequest){
        if(nodeMapper.selectByNodeId(nodeRequest.getNodeId()) != null){
            throw new BaseException(ErrorCodeAndMsg.Node_is_already_exist);
        }
        Node node = new Node();
        node.setName(nodeRequest.getName());
        node.setNodeId(nodeRequest.getNodeId());
        nodeMapper.saveNode(node);
        return Response.successResponse("添加节点成功");
    }
}
