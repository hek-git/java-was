package codesquad.handler;

import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;
import codesquad.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CreateHandler implements Handler {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String REDIRECT_URL = "http://localhost:8080";

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> stringStringMap = parseUserInfo(request.path());

        String userId = stringStringMap.get("userId");
        String password = stringStringMap.get("password");
        String name = stringStringMap.get("name");

        User user = new User(userId, password, name);
        log.info(user.toString());

        return new HttpResponse(REDIRECT_URL);
    }

    public static Map<String, String> parseUserInfo(String url) {
        Map<String, String> userInfo = new HashMap<>();

        // URL에서 쿼리 문자열 추출
        int queryStartIndex = url.indexOf('?');
        if (queryStartIndex == -1) {
            return userInfo; // 쿼리 문자열이 없으면 빈 맵 반환
        }

        String queryString = url.substring(queryStartIndex + 1);

        // 쿼리 문자열을 파라미터로 분리
        String[] params = queryString.split("&");

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
