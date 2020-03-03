package com.ops.gateway.mapper;

import com.ops.gateway.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User selectByUserName(String userName);
}
