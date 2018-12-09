package com.upwork.service.impl;

import com.upwork.dao.UserDao;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.upwork.model.UserDetails user = userDao.findByUserName(username);
        User.UserBuilder builder = null;
        if (user != null) {

            builder = User.withUsername(username);
            builder.password(user.getPassword());
            String[] roles = user.getRoles()
                    .stream().map(a -> a.getRole()).toArray(String[]::new);

            builder.authorities(roles);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}
