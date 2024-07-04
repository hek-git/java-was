package codesquad.handler;

import codesquad.file.FileReader;
import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;

public class StaticResourceHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        return new HttpResponse(request.path(), FileReader.getContent(request.path()));
    }
}
