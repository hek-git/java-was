package codesquad.handler;

import codesquad.database.UserDatabase;
import codesquad.util.ContentTypeMapper;
import codesquad.util.DirectoryMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HandlerMapper {

    private static final Map<String, Handler> handlerMapper = Map.of(
        "/user/create", new UserCreateHandler(),
        "/user/login", new UserLoginHandler(),
            "/user/list", new UserListHandler()
    );

    private HandlerMapper() {
    }

    public static Handler findHandler(String uri) {

        Optional<Handler> handler = handlerMapper.entrySet().stream()
                .filter(entry -> uri.equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();

        if(handler.isPresent()){
            return handler.get();
        }
        if (ContentTypeMapper.supports(uri) || DirectoryMapper.isDirectory(uri)) {
            return new StaticResourceHandler();
        }
        return null;
    }

}