package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableCaching
public class DemoApplication
        implements CommandLineRunner {

    private final UserRepository userRepository;

    private final UserService userService;

    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public DemoApplication(UserService userService, UserRepository userRepository,
            RedisConnectionFactory redisConnectionFactory) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args).close();
    }

    @Override
    public void run(String... args) {
        resetRedisData();
        User r1 = createR1ToDatabase();
        System.out.println("saved to DB: " + r1);
        User loadedR1 = loadR1(r1);
        System.out.println("first load from DB (and cached to Redis): " + r1);
        loadedR1 = loadR1(loadedR1);
        System.out.println("first load from Redis: " + loadedR1);
        updateFields(r1);
        r1 = saveR1(r1);
        System.out.println("updated: " + r1);
        loadedR1 = loadR1(r1);
        System.out.println("second load from Redis: " + loadedR1);
    }

    private void resetRedisData() {
        redisConnectionFactory.getConnection().flushAll();
    }

    private User createR1ToDatabase() {
        return userRepository.save(getR1());
    }

    private void updateFields(User r1) {
        r1.setName("oriol");
    }

    private User loadR1(User user) {
        return userService.get(user.getId());
    }

    private User getR1() {
        return User.builder().name("ivan").build();
    }

    private User saveR1(User user) {
        return userRepository.save(user);
    }
}
