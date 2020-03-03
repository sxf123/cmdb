package com.ops.host.config;

import com.ops.host.entity.Host;
import com.ops.host.mapper.HostMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@Order(1)
public class CacheData implements ApplicationRunner {
    @Resource
    HostMapper hostMapper;

    @Resource
    HostCache hostCache;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        synchronizationHost();
        syncronizetionMulHost();
        syncronizetionMulHost2();
    }

    public void synchronizationHost() {
        List<Host> hostList = hostMapper.findAllHost();
        for(Host host : hostList){
            StringBuilder builder = new StringBuilder();
            builder.append(host.getClass().toString() + "::" + host.getHostName());
            hostCache.cachePutObject(builder.toString(),host);
        }
    }

    public void syncronizetionMulHost() {
        for(int i=0;i<10000;i++){
            Host host = new Host();
            host.setHostName("cmdb0" + i);
            host.setHostType("virtual");
            host.setIpAddress("192.168.0.1");
            StringBuilder builder = new StringBuilder();
            builder.append(host.getClass().toString() + "::" + host.getHostName());
            hostCache.cachePutObject(builder.toString(),host);
            System.out.println("Cache 主机 cmdb0" + i);
        }
    }

    public void syncronizetionMulHost2() {
        for(int i=10000;i<20000;i++){
            Host host = new Host();
            host.setHostName("cmdb0" + i);
            host.setHostType("virtual");
            host.setIpAddress("192.168.0.1");
            StringBuilder builder = new StringBuilder();
            builder.append(host.getClass().toString() + "::" + host.getHostName());
            hostCache.cachePutObject(builder.toString(),host);
            System.out.println("Cache 主机 cmdb0" + i);
        }
    }
}
