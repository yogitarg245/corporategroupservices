package com.user.management.service;

import com.user.management.persistance.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User findUserByEmail(String email);

    void saveUser(User user);
    public String extractUsername(String authHeader);
    User findUserById(Integer id);

    List<User> findAll();

    void deleteUser(String email);
    List<String> getAuthoritiesByEmail(String email);

    boolean getUserIsEnable(String email);

    List<User> findUserByEmailAndFirstNameAndLastName(String email, String firstName, String lastName);
}
