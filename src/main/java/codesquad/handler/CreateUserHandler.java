package codesquad.handler;

import codesquad.http.HttpStatus;
import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;
import codesquad.model.User;
import codesquad.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CreateUserHandler implements Handler {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    public CreateUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        Map<String, String> stringMap;
        try {
            stringMap = parseUserInfo(new String(request.body(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String userId = stringMap.get("userId");
        String password = stringMap.get("password");
        String name = stringMap.get("name");

        User user = new User(userId, password, name);
        userRepository.addUser(user);
        log.info(user.toString());
        return new HttpResponse("/");
    }

    private static Map<String, String> parseUserInfo(String body) {
        Map<String, String> userInfo = new HashMap<>();

        // 쿼리 문자열을 파라미터로 분리
        String[] params = body.split("&");

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                // userId, password, name만 추출
                if (key.equals("userId") || key.equals("password") || key.equals("name")) {
                    userInfo.put(key, value);
                }
            }
        }
        return userInfo;
    }

}
