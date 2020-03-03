package com.ops.account.mapper;

import com.ops.account.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User selectByUserName(String userName);
    void saveUser(User user);
}
