package com.ops.host.request;

import com.ops.host.entity.Node;

import java.util.List;

public class HostRequest {
    private String hostName;
    private String ipAddress;
    private String hostType;
    private List<HostNodeRequest> hostNodeRequests;

    public List<HostNodeRequest> getHostNodeRequests() {
        return hostNodeRequests;
    }

    public void setHostNodeRequests(List<HostNodeRequest> hostNodeRequests) {
        this.hostNodeRequests = hostNodeRequests;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }
}
