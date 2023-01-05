package com.mateuszczyz.Todo.repository;

import com.mateuszczyz.Todo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_FindByEmail_ReturnsUserIfExists() {
        String email = "test@gmail.com";
        User user = User.builder()
                .email(email)
                .password("test")
                .build();
        userRepository.save(user);
        Optional<User> queryResult = userRepository.findByEmail(email);
        assertThat(queryResult.isPresent()).isTrue();
    }

    @Test
    public void UserRepository_FindByEmail_ReturnsEmptyOptionalIfUserNotExists() {
        userRepository.deleteAll();
        Optional<User> queryResult = userRepository.findByEmail("test");
        assertThat(queryResult.isPresent()).isFalse();
    }

    @Test
    public void UserRepository_ExistsByEmail_ReturnsTrueIfUserExists() {
        String email = "test@gmail.com";
        User user = User.builder()
                .email(email)
                .password("test")
                .build();
        userRepository.save(user);
        assertThat(userRepository.existsByEmail(email)).isTrue();
    }

    @Test
    public void UserRepositoryExistsByEmail_ReturnsFalseIfUserNotExists() {
        userRepository.deleteAll();
        assertThat(userRepository.existsByEmail("test")).isFalse();
    }
}