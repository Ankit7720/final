package com.uxpsystems.assignement.controller;

import com.uxpsystems.assignement.models.User;
import com.uxpsystems.assignement.service.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    public void testGetAll() throws Exception {

        User user = new User("ankit", passwordEncoder.encode("ankit123"), "ADMIN", true);
        User user1 = new User("ank", passwordEncoder.encode("ankit123"), "ADMIN", true);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);

        Mockito.when(userDetailsService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/assignement/admin/getAll"))
                .andExpect(status().isOk()).andDo(print());
    }


    @Test
    public void testGetUserById() throws Exception {

        User user = new User("ankit", passwordEncoder.encode("ankit123"), "ADMIN", true);
        Mockito.when(userDetailsService.getUserById(anyLong())).thenReturn(java.util.Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/assignement/admin/get/1")).andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteUserById() throws Exception {
        User user = new User("ankit", passwordEncoder.encode("ankit123"), "ADMIN", true);
        userDetailsService.addUser(user);
        Mockito.when(userDetailsService.deleteUser(anyLong())).thenReturn("User deleted Successfully!!!");

        mockMvc.perform(MockMvcRequestBuilders.delete("/assignement/admin/delete/1")).andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void testAddUserByAdmin() throws Exception {

        User user = new User("ankit", passwordEncoder.encode("ankit123"), "ADMIN", true);
        userDetailsService.addUser(user);
        Mockito.when(userDetailsService.addUser(any(User.class))).thenReturn("User Added Successfully!!!");

        String jsonInput = "{\n" +
                "  \"username\": \"ankit\",\n" +
                "  \"password\": \"ankit123\",\n" +
                "  \"roles\": \"USER\",\n" +
                "  \"active\": true\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/assignement/admin/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInput))
                .andExpect(status().isOk()).andDo(print());
    }


    @Test
    public void testUpdateUserByAdmin() throws Exception {
        User user = new User("ankit", passwordEncoder.encode("ankit123"), "ADMIN", true);
        userDetailsService.addUser(user);
        Mockito.when(userDetailsService.updateUser(anyLong(),any(User.class))).thenReturn("User updated Successfully!!!");
        String jsonInput = "{\n" +
                "  \"username\": \"jerry\",\n" +
                "  \"password\": \"jerry123\",\n" +
                "  \"roles\": \"ADMIN\",\n" +
                "  \"active\": true\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/assignement/admin/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInput))
                .andExpect(status().isOk()).andDo(print());
    }

}
