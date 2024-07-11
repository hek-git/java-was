package codesquad.handler;

import codesquad.http.HttpStatus;
import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;
import codesquad.model.User;
import codesquad.database.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserCreateHandler implements Handler {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserDatabase userDatabase = new UserDatabase();

    public UserCreateHandler() {
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.method().equals("POST")) {
            return doPost(request);
        }
        return new HttpResponse(HttpStatus.METHOD_NOT_ALLOWED, "text", new byte[0]);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        return new HttpResponse(HttpStatus.METHOD_NOT_ALLOWED, "text", new byte[0]);
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        User user;
        try {
            user = parseUserInfo(new String(request.body(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        if(user.isNull()){
            return new HttpResponse(HttpStatus.BAD_REQUEST, "text", "".getBytes());
        }

        userDatabase.addUser(user);
        log.info(user.toString());
        return new HttpResponse("/");
    }

    private User parseUserInfo(String body) {
        Map<String, String> userInfo = new HashMap<>();
        // 쿼리 문자열을 파라미터로 분리
        String[] params = body.split("&");

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                userInfo.put(keyValue[0], keyValue[1]);
            }
        }
        return new User(userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"));
    }

}
