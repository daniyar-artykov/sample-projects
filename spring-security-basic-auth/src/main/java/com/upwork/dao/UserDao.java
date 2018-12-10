package com.upwork.dao;

import com.upwork.model.UserDetails;

public interface UserDao {

    UserDetails findByUserName(String username);
}
