package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DemoApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public DemoApplication(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        resetRedisData();
        User r1 = saveR1(getR1());
        log.info("saved to DB: {}", r1);
        User loadedR1 = loadR1(r1);
        log.info("first load from DB (and cached to Redis): {}", r1);
        loadedR1 = loadR1(loadedR1);
        log.info("first load from Redis: {}", loadedR1);
        updateFields(r1);
        r1 = saveR1(r1);
        log.info("updated");
        loadedR1 = loadR1(r1);
        log.info("second load from Redis: {}", loadedR1);
    }

    private void updateFields(User r1) {
        r1.setName("oriol");
    }

    private User loadR1(User user) {
        return userService.get(String.valueOf(user.getId()));
    }

    private User getR1() {
        return User.builder().name("ivan").build();
    }

    private User saveR1(User user) {
        return userRepository.save(user);
    }
}
