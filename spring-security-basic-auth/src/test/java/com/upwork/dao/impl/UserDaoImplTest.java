package com.upwork.dao.impl;

import com.upwork.TestApplication;
import com.upwork.TestSpringSecurityConfig;
import com.upwork.dao.UserDao;
import com.upwork.model.Role;
import com.upwork.model.UserDetails;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author daniyar.artykov
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestApplication.class, TestSpringSecurityConfig.class})
@DataJpaTest
public class UserDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDao userDao;

    @Test
    public void testFindByUserName() {
        String username = "admin";
        UserDetails user = userDao.findByUserName(username);
        assertEquals(username, user.getUsername());
        user.getRoles().forEach(r -> System.out.println(r.getRole()));
        Role role = user.getRoles().stream().filter(r -> "ROLE_ADMIN".equals(r.getRole()))
                .findAny().orElse(null);
        assertNotNull(role);
        assertNotNull(user.getId());
    }

    @Test
    public void testFindByUserName_whenUserNotFound() {
        String username = "admin122";
        UserDetails user = userDao.findByUserName(username);
        assertNull(user);
    }
}
