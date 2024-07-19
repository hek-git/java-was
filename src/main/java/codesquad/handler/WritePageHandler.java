package codesquad.handler;

import codesquad.database.PostH2Database;
import codesquad.database.SessionDatabase;
import codesquad.file.FileReader;
import codesquad.http.HttpStatus;
import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;
import codesquad.model.Post;
import codesquad.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class WritePageHandler implements Handler {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static SessionDatabase sessionDatabase = SessionDatabase.getInstance();
    private final static PostH2Database postH2Database = PostH2Database.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) throws RuntimeException {
        if (request.method().equals("GET")) {
            return doGet(request);
        }
        if (request.method().equals("POST")) {
            try {
                return doPost(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new HttpResponse(HttpStatus.METHOD_NOT_ALLOWED, "text", new byte[0]);
    }


    public HttpResponse doGet(HttpRequest request) throws RuntimeException {
        byte[] content = FileReader.getContent("/static/write/write.html");
        String s = new String(content);
        Optional<String> cookie = request.getCookie();
        if(cookie.isPresent()) {
            Optional<User> userOptional = sessionDatabase.findUserBySessionId(cookie.get().split("=")[1]);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                s = s.replace("{{username}}", user.getName());
            }
        }
        return new HttpResponse(HttpStatus.OK, "html", s.getBytes());
    }


    public HttpResponse doPost(HttpRequest request) throws IOException {
        Optional<String> cookie = request.getCookie();
        if(cookie.isPresent()) {
            Optional<User> userOptional = sessionDatabase.findUserBySessionId(cookie.get().split("=")[1]);
            if(userOptional.isPresent()) {
                parseFormData(request, userOptional.get());
            }
        }

        return new HttpResponse("/");
    }

    private void parseFormData(HttpRequest request, User user) throws IOException {

        String contentType = request.headers().get("Content-Type");
        byte[] body = request.body();
        byte[] boundaryByte = ("--" + contentType.split("=")[1]).getBytes();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for(int i = 0; i < boundaryByte.length + 2; i++) {
            inputStream.read();
        }

        int b;
        String content = "", imagePath = "";
        byte[] parsedBody;
        boolean isBody = false;
        Map<String, String> headerMap = new HashMap<>();

        while((b = inputStream.read()) != -1) {

            if(!isBody && endsWith(outputStream, "\r\n\r\n".getBytes())) {
                String[] headers = outputStream.toString().trim().split("\r\n");

                for(String header : headers) {
                    String[] headerSplit = header.split(": ");
                    headerMap.put(headerSplit[0], headerSplit[1]);
                }
                outputStream.reset();
                isBody = true;
            }

            if(endsWith(outputStream, boundaryByte)) {
                parsedBody = extractBody(outputStream, boundaryByte);
                outputStream.reset();
                if(headerMap.get("Content-Type") != null) {
                    String fileName = headerMap.get("Content-Disposition").split(";")[2].split("\"")[1];
                    imagePath = extractFile(parsedBody, fileName);
                    log.info("imagePath : {}", imagePath);
                } else {
                    content = new String(parsedBody);
                }
                isBody = false;
                headerMap.clear();
            }
            outputStream.write(b);
        }

        postH2Database.addPost(new Post(user, content, imagePath));
        inputStream.close();
        outputStream.close();
    }

    private boolean endsWith(ByteArrayOutputStream outputStream, byte[] bytes) {
        byte[] outputBytes = outputStream.toByteArray();
        if(outputBytes.length < bytes.length) {
            return false;
        }
        for(int i = 0; i < bytes.length; i++) {
            if(outputBytes[outputBytes.length - bytes.length + i] != bytes[i]) {
                return false;
            }
        }
        return true;
    }

    private byte[] extractBody(ByteArrayOutputStream outputStream, byte[] boundaryByte) {
        int bodyLength = outputStream.size() - boundaryByte.length - 2;
        byte[] bytes = new byte[bodyLength];
        System.arraycopy(outputStream.toByteArray(), 0, bytes, 0, bodyLength);
        return bytes;
    }

    private String extractFile(byte[] body, String extension) {
        String homeDir = System.getProperty("user.home");
        String photoDirectoryPath = homeDir + "/photo";
        File photoDirectory = new File(photoDirectoryPath);


        if (!photoDirectory.exists()) {
            photoDirectory.mkdirs();
        }

        try {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + extension;
            File outputFile = new File(photoDirectory, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            fileOutputStream.write(body);
            fileOutputStream.flush();
            fileOutputStream.close();
            return "/photo/" + fileName;
        } catch (IOException e) {
            log.info(e.getMessage());
            return null;
        }
    }

}