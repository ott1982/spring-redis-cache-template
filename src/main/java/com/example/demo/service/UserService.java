package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Caching(
            cacheable = {
                    @Cacheable(value = "users", key = "#id")
            },
            put = {
                    @CachePut(value = "users", key = "#id")
            })
    public User get(String id) {
        return userRepository.findById(Long.valueOf(id))
                .orElse(null);
    }
}
