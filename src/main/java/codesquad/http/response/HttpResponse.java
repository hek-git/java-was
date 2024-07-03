package codesquad.http.response;

import java.util.Map;

public record HttpResponse(String HTTP_VERSION, int httpStatusCode, String reasonPhrase, Map<String, String> headers, String body){

    public HttpResponse(int httpStatusCode, String reasonPhrase, Map<String, String> headers, String body) {
        this("HTTP/1.1", httpStatusCode, reasonPhrase, headers, body);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_VERSION).append(" ").append(httpStatusCode).append(" ").append(reasonPhrase).append("\r\n");
        headers.forEach((key, value) -> sb.append(key).append(": ").append(value).append("\r\n"));
        sb.append("\r\n");
        sb.append(body);
        return sb.toString();
    }

}
