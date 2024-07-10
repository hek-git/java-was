package codesquad.handler;

import codesquad.repository.UserRepository;
import codesquad.util.ContentTypeMapper;
import codesquad.util.DirectoryMapper;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {

    private static final Map<String, Handler> handlerMapper = new HashMap<>();

    static {
        handlerMapper.put("/create", new CreateUserHandler(new UserRepository()));
    }

    private HandlerMapper() {
    }

    public static Handler findHandler(String uri) {
        if (ContentTypeMapper.supports(uri) || DirectoryMapper.isDirectory(uri)) {
            return new StaticResourceHandler();
        }

        return handlerMapper.entrySet().stream()
                .filter(entry -> uri.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

}