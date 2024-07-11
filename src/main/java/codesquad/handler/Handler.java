package codesquad.handler;

import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;

public interface Handler {

    HttpResponse handle(HttpRequest request) throws Exception;
    HttpResponse doGet(HttpRequest request) throws Exception;
    HttpResponse doPost(HttpRequest request) throws Exception;

}
