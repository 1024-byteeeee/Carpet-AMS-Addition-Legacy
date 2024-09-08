package club.mcams.carpet.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public static String readFile(String path) throws IOException {
        InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("Null input stream from path " + path);
        }
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }
}
