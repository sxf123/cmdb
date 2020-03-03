package com.ops.host.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class HostCache {

    @Autowired
    CacheManager cacheManager;

    public int cachePutObject(String key, Object o){
        Cache cache = cacheManager.getCache("Host");
        cache.put(key,o);
        return 1;
    }
}
