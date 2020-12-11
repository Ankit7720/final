package com.uxpsystems.assignement.dao;
import com.uxpsystems.assignement.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Delete all
        this.userRepository.deleteAll();

        // Create users
        User jerry = new User("jerry",passwordEncoder.encode("jerry123"),"USER",true);
        User admin = new User("admin",passwordEncoder.encode("admin123"),"ADMIN",true);
        List<User> users = Arrays.asList(jerry,admin);

        // Save to db
        this.userRepository.saveAll(users);
    }
}