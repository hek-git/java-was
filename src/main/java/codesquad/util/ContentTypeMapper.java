package codesquad.util;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeMapper {

    private static final Map<String, String> contentTypeMap = new HashMap<>();

    static {
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("css", "text/css");
        contentTypeMap.put("js", "application/javascript");
        contentTypeMap.put("text", "text/plain");
        contentTypeMap.put("ico", "image/x-icon");
        contentTypeMap.put("png", "image/png");
        contentTypeMap.put("jpg", "image/jpeg");
        contentTypeMap.put("jpeg", "image/jpeg");
        contentTypeMap.put("svg", "image/svg+xml");
    }

    public static boolean supports(String filePath) {
        return getContentTypeFromPath(filePath) != null;
    }

    public static String getContentType(String extension) {
        return contentTypeMap.get(extension.toLowerCase());
    }

    public static String getContentTypeFromPath(String filePath) {
        String extension = extractExtension(filePath);
        return getContentType(extension);
    }

    private static String extractExtension(String filePath) {
        if (filePath == null || !filePath.contains(".")) {
            return "";
        }
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filePath.length() - 1) {
            return "";
        }
        return filePath.substring(lastDotIndex + 1);
    }

}
