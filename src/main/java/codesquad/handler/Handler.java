package codesquad.handler;

import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;

public interface Handler {

    HttpResponse handle(HttpRequest request);
    HttpResponse doGet(HttpRequest request);
    HttpResponse doPost(HttpRequest request);

}
