package codesquad.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String HTTP_VERSION = "HTTP/1.1";
    private int httpStatusCode;
    private String reasonPhrase;
    private final Map<String, String> headers = new HashMap<>();
    private byte[] body;


    public HttpResponse(byte[] body) {
        setMessageHeader(body.length);
        setMessageBody(body);
    }

    public byte[] getBody() {
        return body;
    }

    public byte[] toByteArray() {
        StringBuilder responseBuilder = new StringBuilder();

        // Status line
        responseBuilder.append(HTTP_VERSION)
                .append(" ")
                .append(httpStatusCode)
                .append(" ")
                .append(reasonPhrase)
                .append("\r\n");

        // Headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            responseBuilder.append(header.getKey())
                    .append(": ")
                    .append(header.getValue())
                    .append("\r\n");
        }

        // Blank line between headers and body
        responseBuilder.append("\r\n");

        // Convert headers and status line to byte array
        return responseBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }

    private void setMessageHeader(int contentLength) {



        // body, header를 분리하는 방식(커지면)
        this.httpStatusCode = 200;
        this.reasonPhrase = "OK";
        headers.put("Content-Length", String.valueOf(contentLength));
    }

    private void setMessageBody(byte[] body) {
        this.body = body;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_VERSION).append(" ").append(httpStatusCode).append(" ").append(reasonPhrase).append("\r\n");
        headers.forEach((key, value) -> sb.append(key).append(": ").append(value).append("\r\n"));
        sb.append("\r\n");
        sb.append(new String(body));
        return sb.toString();
    }

}
