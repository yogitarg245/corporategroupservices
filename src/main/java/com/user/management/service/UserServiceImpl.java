package com.user.management.service;

import com.user.management.persistance.entities.User;
import com.user.management.persistance.repository.RoleRepository;
import com.user.management.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findUserById(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().limit(10).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String email) {
        User user=findUserByEmail(email);
        userRepository.delete(user);
    }

    @Override
    public List<String> getAuthoritiesByEmail(String email) {
        return userRepository.getAuthoritiesByEmail(email);
    }

    @Override
    public boolean getUserIsEnable(String email) {
        return userRepository.getUserIsEnable(email);
    }

    @Override
    public List<User> findUserByEmailAndFirstNameAndLastName(String email, String firstName, String lastName) {
        return userRepository.findUserByEmailAndFirstNameAndLastName(email,firstName,lastName);
    }

    @Override
    public String extractUsername(String authHeader) {
        byte[] decodedBytes = Base64.getDecoder().decode(authHeader.split(" ")[1]);
        String decodedUsername = new String(decodedBytes).split(":")[0];

        return new String(decodedUsername);
    }
}
