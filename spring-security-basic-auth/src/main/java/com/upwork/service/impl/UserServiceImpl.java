package com.upwork.service.impl;

import com.upwork.dao.UserDao;
import com.upwork.model.UserDetails;
import com.upwork.service.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserDetails> getUserDetails() {
        return userDao.getUserDetails();
    }
}
