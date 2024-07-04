package codesquad.util;

import java.util.HashMap;
import java.util.Map;

public class DirectoryMapper {

    private static final Map<String, String> directoryMap = new HashMap<>();

    static {
        directoryMap.put("/", "/index.html");
        directoryMap.put("/login", "/login/index.html");
        directoryMap.put("/main", "/logout/index.html");
        directoryMap.put("/registration", "/registration/index.html");
        directoryMap.put("/comment", "/comment/index.html");
        directoryMap.put("/article", "/article/index.html");
    }

    public static boolean isDirectory(String filePath) {
        return getStaticResourcePath(filePath) != null;
    }

    public static String getStaticResourcePath(String directoryPath) {
        return directoryMap.get(parseLowestDirectory(directoryPath));
    }

    private static String parseLowestDirectory(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "/";
        }

        int lastSlashIndex = filePath.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < filePath.length() - 1) {
            String lowestDirectory = filePath.substring(lastSlashIndex + 1);
            return "/" + lowestDirectory;
        }

        return "/";
    }

}
