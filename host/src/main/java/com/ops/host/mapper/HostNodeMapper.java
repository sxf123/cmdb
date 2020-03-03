package com.ops.host.mapper;

import com.ops.host.entity.HostNode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostNodeMapper {
    void saveHostNode(List<HostNode> hostNodeList);
    HostNode selectByHostIdNodeId(HostNode hostNode);
    void deleteHostNode(Integer hostId);
}
