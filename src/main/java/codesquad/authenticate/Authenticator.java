package codesquad.authenticate;

import codesquad.database.SessionDatabase;
import codesquad.http.request.HttpRequest;
import codesquad.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Authenticator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SessionDatabase sessionDatabase = new SessionDatabase();
    private final static List<String> AUTH_REQUIRED_PATHS = List.of("/user/list", "/user/logout");

    public boolean auth(HttpRequest request) {

        String sid = getSid(request.headers());
        if(sid != null) {
            Optional<User> user = sessionDatabase.findUserBySessionId(getSid(request.headers()));
            if(AUTH_REQUIRED_PATHS.stream().noneMatch(path -> path.equals(request.path()))) return true;
            return user.isPresent();
//            user.ifPresent(value -> log.info("로그인한 사용자 {}", value));
        }
        return false;
    }

    private String getSid(Map<String, String> headers) {
        if(headers.get("Cookie") == null)
            return null;
        return headers.get("Cookie").split("=")[1];
    }

}
