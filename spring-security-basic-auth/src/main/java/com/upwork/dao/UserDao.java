package com.upwork.dao;

import com.upwork.model.UserDetails;
import java.util.List;

public interface UserDao {

    UserDetails findByUserName(String username);
    
    List<UserDetails> getUserDetails();
}
