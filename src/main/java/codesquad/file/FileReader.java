package codesquad.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FileReader {

    private final static String STATIC_PATH = "static";
    private final static Logger log = LoggerFactory.getLogger(FileReader.class);

    public static byte[] getContent(String resourceName) {

        URL resource = FileReader.class.getClassLoader().getResource(STATIC_PATH + resourceName);
        if (resource == null) {
            throw new RuntimeException("Resource not found: " + resourceName);
        }
        File file = new File(resource.getPath());

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            if (file.isFile() && file.exists()) {
                byte[] content = new byte[(int) file.length()];
                int read = fileInputStream.read(content);
                if (read != file.length()) {
                    throw new RuntimeException("could not read file");
                }
                fileInputStream.close();
//                log.info("{}", new String(content));
                return content;

            } else {
                throw new FileNotFoundException(file.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
