package codesquad.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final Map<String, String> contentTypeMap = new HashMap<>();
    private final String HTTP_VERSION = "HTTP/1.1";
    private int httpStatusCode;
    private String reasonPhrase;
    private final Map<String, String> headers = new HashMap<>();
    private byte[] body;

    static {
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("css", "text/css");
        contentTypeMap.put("js", "application/javascript");
        contentTypeMap.put("ico", "image/x-icon");
        contentTypeMap.put("png", "image/png");
        contentTypeMap.put("jpg", "image/jpeg");
        contentTypeMap.put("svg", "image/svg+xml");
    }

    public HttpResponse(String filePath, byte[] body) {
        setMessageHeader(filePath, body.length);
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
        return responseBuilder.toString().getBytes();
    }

    private void setMessageHeader(String filePath, int contentLength) {

        String contentType = contentTypeMap.get(getFileExtension(filePath));

        if (contentType == null) {
            this.httpStatusCode = 415;
            this.reasonPhrase = "Unsupported Media Type";
            return;
        }

        // body, header를 분리하는 방식(커지면)
        this.httpStatusCode = 200;
        this.reasonPhrase = "OK";
        headers.put("Content-Type", contentType);
        headers.put("Content-Length", String.valueOf(contentLength));
    }

    private void setMessageBody(byte[] body) {
        this.body = body;
    }

    private String getFileExtension(String filePath) {
        int lastIndexOfDot = filePath.lastIndexOf('.');
        int lastIndexOfSlash = filePath.lastIndexOf('/');

        if (lastIndexOfDot > lastIndexOfSlash && lastIndexOfDot != -1) {
            return filePath.substring(lastIndexOfDot + 1);
        }
        return "";
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
