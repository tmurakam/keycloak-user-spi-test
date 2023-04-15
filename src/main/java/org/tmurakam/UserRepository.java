package org.tmurakam;

import java.util.Arrays;
import java.util.List;

public class UserRepository {

    private final List<User> USERS;

    UserRepository() {
        USERS = Arrays.asList(
                new User("1", "john", "John", "Doe", "john@example.com")
        );
    }

    User findUserById(String id) {
        return USERS.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    User findUserByUsername(String username) {
        return USERS.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    User findUserByEmail(String email) {
        return USERS.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    boolean updateCredentials(User user, String password) {
        user.setPassword(password);
        return true;
    }
}
