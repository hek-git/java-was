package codesquad.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {

    private final Logger log = LoggerFactory.getLogger(HttpRequestParser.class);

    public HttpRequest parse(BufferedReader bufferedReader) throws IOException {

        String line = bufferedReader.readLine();
        String[] requests;
        Map<String, String> headers = new HashMap<>();

//         Parse request line
        if (line == null) {
            throw new NullPointerException("requestLine is null");
        }
        requests = line.split(" ");

//         Parse headers
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            String[] headerParts = line.split(":", 2);
            if (headerParts.length == 2) {
                headers.put(headerParts[0].trim(), headerParts[1].trim());
            }
        }

//        Parse body
        String s = headers.get("Content-Length");
        if (s != null) {
            int length = Integer.parseInt(s);
            char[] buffer = new char[length];
            bufferedReader.read(buffer, 0, length);
            byte[] body = new String(buffer).getBytes("UTF-8");
//            log.info("body : {}", new String(body));
            return new HttpRequest(requests[0], requests[1], requests[2], headers, body);
        }
        return new HttpRequest(requests[0], requests[1], requests[2], headers, "".getBytes());
    }
}
