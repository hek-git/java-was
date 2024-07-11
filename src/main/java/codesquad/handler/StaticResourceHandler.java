package codesquad.handler;

import codesquad.file.FileReader;
import codesquad.http.HttpStatus;
import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;
import codesquad.util.DirectoryMapper;

public class StaticResourceHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        if(request.method().equals("GET")) {
            return doGet(request);
        }
        return doPost(request);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        String mappedPath = DirectoryMapper.getStaticResourcePath(request.path());
        if (mappedPath != null) {
            return new HttpResponse(HttpStatus.OK, mappedPath, FileReader.getContent(mappedPath));
        }
        return new HttpResponse(HttpStatus.OK, request.path(), FileReader.getContent(request.path()));
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        return new HttpResponse(HttpStatus.METHOD_NOT_ALLOWED, "text", new byte[0]);
    }
}
