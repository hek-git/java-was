package codesquad.http.request;

import java.util.Map;

public record HttpRequest(String method, String path, String version, Map<String, String> headers, String body) {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Method: ").append(method).append("\n");
        sb.append("Path: ").append(path).append("\n");
        sb.append("Version: ").append(version).append("\n");
        headers.forEach((key, value) -> sb.append(key).append(": ").append(value).append("\n"));
        return sb.toString();
    }
}
