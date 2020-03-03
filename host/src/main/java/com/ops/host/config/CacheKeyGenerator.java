package com.ops.host.config;

import com.ops.host.entity.Host;
import com.ops.host.service.HostService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@EnableCaching
public class CacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object o, Method method, Object... objects){
        StringBuilder builder = new StringBuilder();
        if(o instanceof HostService){
            builder.append(o.getClass().toString());
            builder.append("::");
        }else{
            builder.append("com.ops.host::");
        }
        if(objects[0] instanceof Host){
            String fieldKey = ((Host)objects[0]).getHostName();
            builder.append(fieldKey);
        }else{
            builder.append("NULL");
        }
        return builder;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

//    public RedisCacheConfiguration serializationContext(Class t) {
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(t);
//        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);
//        return RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
//    }
//
//    @Bean
//    public CacheManager hostCacheManager(RedisConnectionFactory redisConnectionFactory){
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
//        return new RedisCacheManager(redisCacheWriter, serializationContext(Host.class));
//    }
}
