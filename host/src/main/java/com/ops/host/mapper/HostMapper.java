package com.ops.host.mapper;

import com.ops.host.entity.Host;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostMapper {
    void saveHost(Host host);
    Host selectHostByHostName(String hostName);
    void updateByHostName(String ipAddress,String hostType,String hostName);
    void deleteByHostName(String hostName);
    List<Host> findAllHost();
}
