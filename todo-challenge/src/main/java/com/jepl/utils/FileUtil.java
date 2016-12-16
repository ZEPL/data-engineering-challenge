package com.jepl.utils;

import java.io.*;
import java.nio.file.*;

public class FileUtil {

    public static void writeString(String targetPath, String json) throws IOException {
        Path path = FileSystems.getDefault().getPath(targetPath);
        Files.write( path , json.getBytes());
    }

    public static String readString(String targetPath) throws IOException {
        Path path = FileSystems.getDefault().getPath(targetPath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }
}
