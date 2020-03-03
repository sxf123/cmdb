package com.ops.host.mapper;

import com.ops.host.entity.Node;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeMapper {
    void saveNode(Node node);
    Node selectByNodeId(Integer nodeId);
}
