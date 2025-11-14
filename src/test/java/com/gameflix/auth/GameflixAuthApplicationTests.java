package com.gameflix.auth;

import com.gameflix.auth.service.UserService;
import com.gameflix.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameflixAuthApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void registerAndLoginFlow() {
        String u = "testuser";
        String p = "testpass123";
        userService.register(u, p);
        assertTrue(userService.login(u, p));
        assertFalse(userService.login(u, "badpass"));
        assertTrue(userRepository.existsByUsername(u));
    }
}
