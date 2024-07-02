package codesquad.file;

import java.io.File;
import java.net.URL;

public class FileReader {

    private final String STATIC_PATH = "static";

    public File getFile(String resourceName) {
        URL resource = getClass().getClassLoader().getResource(STATIC_PATH + "/" + resourceName);
        if (resource == null) {
            throw new RuntimeException("Resource not found: " + resourceName);
        }
        return new File(resource.getFile());
    }

}
