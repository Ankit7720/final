package com.uxpsystems.assignement.dao;

import com.uxpsystems.assignement.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepoTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser(){
        User user = new User("ankit","ankit123", "ADMIN", true);
        userRepository.save(user);
        User user1= userRepository.findUserByUsername("ankit");
        assertEquals(user1,user);
    }

    @Test
    public void testGetUser() {

        User user = new User("ankit","ankit123", "ADMIN", true);
        userRepository.save(user);
        User user1 = userRepository.findUserByUsername("ankit");
        assertNotNull(user1);
        assertEquals(user1.getId(), user.getId());
    }

    @Test
    public void testDeleteUser() {
        User user =new User("ankit","ankit123", "ADMIN", true) ;

        userRepository.save(user);

        userRepository.deleteById(user.getId());
    }

    @Test
    public void testGetAllUsers() {
        User user =new User("ankit","ankit123", "ADMIN", true) ;

        userRepository.save(user);
        assertNotNull(
                userRepository.findAll());
    }


}
