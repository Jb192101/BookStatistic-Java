package org.jedi_bachelor.bs.utils;

import org.jedi_bachelor.bs.view.AboutWindow;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static String readDescriptopnProjectFromFile(String filePath) throws IOException {
        try (var inputStream = AboutWindow.class.getResourceAsStream(filePath)) {
            if (inputStream != null) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
            return Files.readString(Paths.get(filePath));
        }
    }
}
