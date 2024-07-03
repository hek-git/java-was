package codesquad.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseBuilder {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final File file;

    public HttpResponseBuilder(File file) {
        this.file = file;
    }

    public HttpResponse build() {

        int length = 0;
        StringBuilder sb = new StringBuilder();
        Map<String, String> headers = new HashMap<>();

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                length += line.getBytes().length;
            }
            headers.put("Content-Length", String.valueOf(length));
            headers.put("Content-Type", "text/html");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new HttpResponse(200, "OK", headers, sb.toString());
    }

}
