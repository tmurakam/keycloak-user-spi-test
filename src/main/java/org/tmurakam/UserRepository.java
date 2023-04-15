package org.tmurakam;

import java.util.Arrays;
import java.util.List;

public class UserRepository {

    private final List<User> USERS;

    public UserRepository() {
        USERS = Arrays.asList(
                new User("1", "john", "John", "Doe", "john@example.com")
        );
    }

    public List<User> getAllUsers() {
        return USERS;
    }

    public User findUserById(String id) {
        return USERS.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User findUserByUsername(String username) {
        return USERS.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public User findUserByEmail(String email) {
        return USERS.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public boolean updateCredentials(User user, String password) {
        user.setPassword(password);
        return true;
    }
}
