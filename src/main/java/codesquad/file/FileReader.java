package codesquad.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileReader {

    private final static Logger log = LoggerFactory.getLogger(FileReader.class);

    private FileReader() {
    }

    public static byte[] getContent(String resourceName) throws RuntimeException {

        InputStream resourceAsStream = FileReader.class.getResourceAsStream(resourceName);

        try {
            if (resourceAsStream == null) {
                throw new FileNotFoundException("Resource not found: " + resourceName);
            }
            return resourceAsStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
