package codesquad.handler;

import codesquad.file.FileReader;
import codesquad.http.request.HttpRequest;
import codesquad.http.request.HttpRequestParser;
import codesquad.http.response.HttpResponse;
import codesquad.http.response.HttpResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        HttpRequestParser httpRequestParser = new HttpRequestParser();
        try {
            HttpRequest request = httpRequestParser.parse(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
            OutputStream clientOutput = clientSocket.getOutputStream();

            log.info("Method : {}",request.method());
            log.info("Path : {}",request.path());
            log.info("Version : {}",request.version());
            log.info("Body: {}",request.body());

            FileReader fileReader = new FileReader();
            File file = fileReader.getFile(request.path());

            HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder(file);
            HttpResponse response = httpResponseBuilder.build();

            clientOutput.write(response.toString().getBytes());
            clientOutput.flush();
            clientOutput.close();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

}
