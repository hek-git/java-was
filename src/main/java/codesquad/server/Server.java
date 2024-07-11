package codesquad.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final Logger log = LoggerFactory.getLogger(Server.class);
    private final ServerSocket serverSocket;
    private final ExecutorService executorService;

    public Server(int port, int poolSize){
        try {
            serverSocket = new ServerSocket(port);
            executorService = Executors.newFixedThreadPool(poolSize);
            log.info("Listening for connection on port 8080 ....");
        } catch (IOException e) {
            log.info("could not create server socket");
            throw new RuntimeException(e);
        }
    }

    public void run() {

        while (true) { // 무한 루프를 돌며 클라이언트의 연결을 기다립니다.
            try {
                Socket clientSocket = serverSocket.accept();  // 클라이언트 연결을 수락합니다.
                log.info("Client connected");
                executorService.execute(new RequestHandler(clientSocket));
            } catch (IOException e) {
                log.info("Cannot accept client connection");
            } catch (RuntimeException e) {
                log.info(e.getMessage());
            }
        }
    }
}
