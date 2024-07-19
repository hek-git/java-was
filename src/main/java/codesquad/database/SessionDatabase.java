package codesquad.database;

import codesquad.model.User;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDatabase {

    private static final ConcurrentHashMap<String, User> sessions = new ConcurrentHashMap<>();
    private static SessionDatabase sessionDatabase;

    private SessionDatabase() {
    }

    public static SessionDatabase getInstance() {
        if (sessionDatabase == null) {
            sessionDatabase = new SessionDatabase();
        }
        return sessionDatabase;
    }

    public void addSession(String sessionId, User user) {
        sessions.put(sessionId, user);
    }

    public Optional<User> findUserBySessionId(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
