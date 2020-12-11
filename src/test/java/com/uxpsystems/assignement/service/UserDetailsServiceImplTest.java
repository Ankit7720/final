package com.uxpsystems.assignement.service;

import com.uxpsystems.assignement.dao.UserRepository;
import com.uxpsystems.assignement.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testAddUser(){
        User user = new User("ankit", passwordEncoder.encode("ankit123"), "ADMIN", true);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        assertThat(userDetailsService.addUser(user)).isEqualTo("User Added Successfully!!!");
    }

    @Test
    public void testGetUserById(){
        User user = new User("ankit", passwordEncoder.encode("ankit123"), "ADMIN", true);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        assertThat(userDetailsService.getUserById(anyLong())).isEqualTo(Optional.of(user));
    }

    @Test
    public void testGetAllUsers(){
        User user = new User("ankit", passwordEncoder.encode("ankit123"), "ADMIN", true);
        User user1 = new User("jerry", passwordEncoder.encode("jerry"), "USER", true);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        assertThat(userDetailsService.getAllUsers()).isEqualTo(userList);
    }

    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setId(2147483649L);
        user.setUsername("jerry");
        user.setPassword("jerry123");
        user.setActive(true);
        user.setRoles("USER");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findById(2147483649L)).thenReturn(Optional.of(user));
        User user1= new User("ankit", passwordEncoder.encode("ankit123"), "ADMIN", true);
        assertThat(userDetailsService.updateUser(2147483649L,user1)).isEqualTo("User Updated");
    }
}
