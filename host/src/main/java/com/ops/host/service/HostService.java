package com.ops.host.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ops.host.entity.Host;
import com.ops.commons.enums.ErrorCodeAndMsg;
import com.ops.commons.exception.BaseException;
import com.ops.host.entity.HostNode;
import com.ops.host.mapper.HostMapper;
import com.ops.commons.response.Response;
import com.ops.host.mapper.HostNodeMapper;
import com.ops.host.request.HostNodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ops.host.request.HostRequest;


import java.util.ArrayList;
import java.util.List;

@Service
public class HostService {

    @Autowired
    HostMapper hostMapper;

    @Autowired
    HostNodeMapper hostNodeMapper;

    public Response saveHost(HostRequest hostRequest){
        String hostName = hostRequest.getHostName();
        String ipAddress = hostRequest.getIpAddress();
        String hostType = hostRequest.getHostType();
        List<HostNodeRequest> hostNodeRequests = hostRequest.getHostNodeRequests();
        try{
            if(hostMapper.selectHostByHostName(hostName) != null){
                throw new BaseException(ErrorCodeAndMsg.Host_already_exist);
            }
            Host host = new Host();
            host.setHostName(hostName);
            host.setIpAddress(ipAddress);
            host.setHostType(hostType);
            hostMapper.saveHost(host);
            Host hostNodeHost = hostMapper.selectHostByHostName(hostName);
            List<HostNode> hostNodeList = new ArrayList<>();
            for(HostNodeRequest hostNodeRequest : hostNodeRequests){
                HostNode hostNode = new HostNode();
                hostNode.setHostId(hostNodeHost.getId());
                hostNode.setNodeId(hostNodeRequest.getNodeId());
                if(hostNodeMapper.selectByHostIdNodeId(hostNode) != null){
                    continue;
                }else{
                    hostNodeList.add(hostNode);
                }
            }
            if(!hostNodeList.isEmpty()){
                hostNodeMapper.saveHostNode(hostNodeList);
            }
            return Response.successResponse("添加主机成功");
        }catch (Exception e){
            if(e instanceof BaseException){
                throw e;
            }else{
                e.printStackTrace();
                throw new BaseException(ErrorCodeAndMsg.Network_error);
            }
        }
    }

    public Response selectHostByHostName(HostRequest hostRequest){
        String hostName = hostRequest.getHostName();
        try{
            Host host = hostMapper.selectHostByHostName(hostName);
            if(host != null){
                hostRequest.setHostName(host.getHostName());
                hostRequest.setIpAddress(host.getIpAddress());
                hostRequest.setHostType(host.getHostType());
                return new Response(hostRequest);
            }else{
                throw new BaseException(ErrorCodeAndMsg.Host_does_not_exist);
            }
        }catch (Exception e){
            if(e instanceof BaseException){
                throw e;
            }else{
                throw new BaseException(ErrorCodeAndMsg.Network_error);
            }
        }
    }

    public Response updateByHostName(HostRequest hostRequest){
        String hostName = hostRequest.getHostName();
        String hostType = hostRequest.getHostType();
        String ipAddress = hostRequest.getIpAddress();
        List<HostNodeRequest> hostNodeRequests = hostRequest.getHostNodeRequests();
        Host host = hostMapper.selectHostByHostName(hostName);
        try{
            if(host == null){
                throw new BaseException(ErrorCodeAndMsg.Host_does_not_exist);
            }
            hostMapper.updateByHostName(ipAddress,hostType,hostName);
            hostNodeMapper.deleteHostNode(host.getId());
            List<HostNode> hostNodeList = new ArrayList<>();
            for(HostNodeRequest hostNodeRequest : hostNodeRequests){
                HostNode hostNode = new HostNode();
                hostNode.setNodeId(hostNodeRequest.getNodeId());
                hostNode.setHostId(host.getId());
                hostNodeList.add(hostNode);
            }
            if(!hostNodeList.isEmpty()){
                hostNodeMapper.saveHostNode(hostNodeList);
            }
            return Response.successResponse("更新主机成功");
        }catch (Exception e){
            if(e instanceof BaseException){
                throw e;
            }else{
                e.printStackTrace();
                throw new BaseException(ErrorCodeAndMsg.Network_error);
            }
        }
    }

    public Response deleteByHostName(HostRequest hostRequest){
        String hostName = hostRequest.getHostName();
        Host host = hostMapper.selectHostByHostName(hostName);
        try {
            if (host != null) {
                hostMapper.deleteByHostName(hostName);
                return Response.successResponse("删除主机成功");
            } else {
                throw new BaseException(ErrorCodeAndMsg.Host_does_not_exist);
            }
        }catch (Exception e){
            if(e instanceof BaseException){
                throw e;
            }else{
                throw new BaseException(ErrorCodeAndMsg.Network_error);
            }
        }
    }

    public Response findAllHost(int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<Host> hostList = hostMapper.findAllHost();
        PageInfo<Host> pageInfo = new PageInfo<>(hostList);
        return new Response(pageInfo);
    }
}
