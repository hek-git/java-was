package codesquad.database;

import codesquad.model.User;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserDatabase {

    private final static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public Optional<User> findUserById(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

}
