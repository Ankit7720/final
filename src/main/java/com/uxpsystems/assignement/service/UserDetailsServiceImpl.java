package com.uxpsystems.assignement.service;


import com.uxpsystems.assignement.dao.UserRepository;
import com.uxpsystems.assignement.models.User;
import com.uxpsystems.assignement.models.UserPrincipal;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable("users")
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new UserPrincipal(user);
    }

    @Secured(value = "ROLE_ADMIN")
    public String addUser(User user) {
        userRepository.save(user);
        return "User Added Successfully!!!";
    }

    @Secured(value = "ROLE_ADMIN,ROLE_USER")
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Secured(value = "ROLE_ADMIN")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Secured(value = "ROLE_ADMIN,ROLE_USER")
    @CachePut(value = "users", key = "#id")
    public String updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setRoles(user.getRoles());
            existingUser.setActive(user.isActive());
            userRepository.save(existingUser);
            return "User Updated";
        }
        return "User Not Found";
    }

    @Secured(value = "ROLE_ADMIN")
    @CacheEvict(value = "users", key = "#id")
    public String deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User Deleted SuccessFully";
        }
        return "User Not Exist";
    }
}
