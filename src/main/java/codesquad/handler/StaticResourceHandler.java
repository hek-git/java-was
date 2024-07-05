package codesquad.handler;

import codesquad.file.FileReader;
import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;
import codesquad.util.DirectoryMapper;

public class StaticResourceHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        String mappedPath = DirectoryMapper.getStaticResourcePath(request.path());
        if (mappedPath != null) {
            return new HttpResponse(mappedPath, FileReader.getContent(mappedPath));
        }
        return new HttpResponse(request.path(), FileReader.getContent(request.path()));
    }
}
