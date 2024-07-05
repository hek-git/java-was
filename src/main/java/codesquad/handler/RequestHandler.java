package codesquad.handler;

import codesquad.file.FileReader;
import codesquad.http.request.HttpRequest;
import codesquad.http.request.HttpRequestParser;
import codesquad.http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final HttpRequestParser httpRequestParser;
//    private final HttpResponseBuilder httpResponseBuilder;
    private FileReader fileReader;
//    private final UrlMapper urlMapper;

    public RequestHandler(Socket clientSocket) throws IOException {
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        this.httpRequestParser = new HttpRequestParser();
//        this.httpResponseBuilder = new HttpResponseBuilder();
//        this.urlMapper = new UrlMapper();
    }

    @Override
    public void run() {

        try {
            HttpRequest request = httpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
            log.info("{}\n", request.toString());

            HttpResponse response = new HttpResponse(request.path(), FileReader.getContent(request.path()));

            outputStream.write(response.toByteArray());
            outputStream.write(response.getBody());

            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}