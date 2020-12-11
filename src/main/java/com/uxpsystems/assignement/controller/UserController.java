package com.uxpsystems.assignement.controller;

import com.uxpsystems.assignement.models.User;
import com.uxpsystems.assignement.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RequestMapping("/assignement")
@RestController
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/admin/add")
    public String addUserByAdmin(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userDetailsService.addUser(user);
    }

    @GetMapping("/admin/getAll")
    public List<User> getAllUsers(){
        return userDetailsService.getAllUsers();
    }

    @GetMapping("/admin/get/{id}")
    public Optional<User> getUserById(@PathVariable Long id){

        return userDetailsService.getUserById(id);
    }

    @GetMapping("/user/get/{id}")
    public Optional<User> getUser(@PathVariable Long id){

        return userDetailsService.getUserById(id);
    }


    @PutMapping("/admin/update/{id}")
    public String updateUserByAdmin(@PathVariable("id") Long id, @RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDetailsService.updateUser(id,user);
    }


    @PutMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userDetailsService.updateUser(id,user);
    }

    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        return userDetailsService.deleteUser(id);
    }


}