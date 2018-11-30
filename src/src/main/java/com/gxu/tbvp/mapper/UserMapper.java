package com.gxu.tbvp.mapper;

import com.gxu.tbvp.domain.User;
import com.gxu.tbvp.utils.MyMapper;

import java.util.Map;

public interface UserMapper extends MyMapper<User> {
    void autoIncrement();

    int countAge(Map<String, Object> age);
}