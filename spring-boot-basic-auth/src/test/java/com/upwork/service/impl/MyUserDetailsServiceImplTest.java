package com.upwork.service.impl;

import com.upwork.dao.UserDao;
import com.upwork.model.Role;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author daniyar.artykov
 */
@RunWith(SpringRunner.class)
public class MyUserDetailsServiceImplTest {

    @TestConfiguration
    static class MyUserDetailsServiceImplTestContextConfiguration {

        @Bean
        public UserDetailsService employeeService() {
            return new MyUserDetailsServiceImpl();
        }
    }

    @MockBean
    private UserDao userDao;

    @Autowired
    private UserDetailsService userDetailsService;

    @Before
    public void setUp() {
        com.upwork.model.UserDetails user = new com.upwork.model.UserDetails();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("admin12");
        Set<Role> roles = new HashSet<>();
        Role r = new Role();
        r.setRole("ADMIN");
        roles.add(r);
        user.setRoles(roles);

        Mockito.when(userDao.findByUserName(user.getUsername()))
                .thenReturn(user);
    }

    @Test
    public void testLoadUserByUsername() {
        String name = "admin";
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        Assert.assertEquals(userDetails.getUsername(), name);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_whenUserNotFound() {
        String name = "john";
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
    }
}
