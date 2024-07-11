package codesquad.http.response;

import codesquad.http.HttpStatus;
import codesquad.util.ContentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String HTTP_VERSION = "HTTP/1.1";
    private HttpStatus httpStatus;
    private final Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public HttpResponse(HttpStatus httpstatus, String filePath, byte[] body) {
        setMessageHeader(httpstatus, filePath, body.length);
        setMessageBody(body);
    }

    public HttpResponse(String redirectUrl){
        setRedirect(redirectUrl);
    }

    public byte[] getBody() {
        return body;
    }

    public byte[] toByteArray() {
        StringBuilder responseBuilder = new StringBuilder();

        // Status line
        responseBuilder.append(HTTP_VERSION)
                .append(" ")
                .append(httpStatus.getStatusCode())
                .append(" ")
                .append(httpStatus.getReasonPhrase())
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
        return responseBuilder.toString().getBytes();
    }

    private void setMessageHeader(HttpStatus httpStatus, String filePath, int contentLength) {

        this.httpStatus = httpStatus;
        String contentType = ContentTypeMapper.getContentTypeFromPath(filePath);

        headers.put("Content-Type", contentType);
        headers.put("Content-Length", String.valueOf(contentLength));
    }

    private void setMessageBody(byte[] body) {
        this.body = body;
    }

    private void setRedirect(String url) {
        this.httpStatus = HttpStatus.FOUND;
        this.headers.put("Location", url);
    }

    public static HttpResponse internalServerError() {
        return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "text", new byte[0]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_VERSION).append(" ").append(httpStatus.getStatusCode()).append(" ").append(httpStatus.getReasonPhrase()).append("\r\n");
        headers.forEach((key, value) -> sb.append(key).append(": ").append(value).append("\r\n"));
        sb.append("\r\n");
        sb.append(new String(body));
        return sb.toString();
    }

}
