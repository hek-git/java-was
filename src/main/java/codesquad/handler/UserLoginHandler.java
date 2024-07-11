package codesquad.handler;

import codesquad.file.FileReader;
import codesquad.http.HttpStatus;
import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;
import codesquad.database.UserDatabase;
import codesquad.util.DirectoryMapper;

import java.util.HashMap;
import java.util.Map;

public class UserLoginHandler implements Handler {

    private final UserDatabase userDatabase;
    private final String LOGIN_FAILED_URL = "/user/login_failed.html";

    public UserLoginHandler(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public HttpResponse handle(HttpRequest request) throws Exception {
        if (request.method().equals("POST")) {
            return doPost(request);
        } else if (request.method().equals("GET")) {
            return doGet(request);
        }
        return new HttpResponse(HttpStatus.METHOD_NOT_ALLOWED, "text", new byte[0]);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) throws Exception {
        String mappedPath = DirectoryMapper.getStaticResourcePath(request.path());
        if (mappedPath != null) {
            return new HttpResponse(HttpStatus.OK, mappedPath, FileReader.getContent(mappedPath));
        }
        return new HttpResponse(HttpStatus.OK, request.path(), FileReader.getContent(request.path()));
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        Map<String, String> stringMap = parseUserInfo(new String(request.body()));
        String userId = stringMap.get("userId");
        String password = stringMap.get("password");

        if(userId == null || password == null)
            return new HttpResponse(HttpStatus.BAD_REQUEST, "text", "".getBytes());

        return userDatabase.findUserById(userId)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> new HttpResponse("/"))
                .orElse(new HttpResponse(LOGIN_FAILED_URL));
    }

    private Map<String, String> parseUserInfo(String body) {

        Map<String, String> userInfo = new HashMap<>();
        // 쿼리 문자열을 파라미터로 분리
        String[] params = body.split("&");

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                userInfo.put(keyValue[0], keyValue[1]);
            }
        }
        return userInfo;
    }
}