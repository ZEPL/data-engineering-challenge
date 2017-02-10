package com.jepl.utils;

import org.junit.*;

import java.io.*;
import java.nio.file.*;

import static org.junit.Assert.assertEquals;

public class ResponseUtilTest {
    @Test
    public void testFileSave() throws IOException {
        String targetPath = "/tmp/todos.json";
        String json = "{\"key\":\"value\"}\"";
        File file = new File(targetPath);
        assertEquals(true, file.exists());
    }

    @Test
    public void testFileRead() throws IOException {
        String targetPath = "/tmp/todos.json";
        String json = "{\"key\":\"value\"}\"";
        FileUtil.writeString(targetPath, json);
        String resultJson =FileUtil.readString(targetPath);
        assertEquals(json, resultJson);
    }
}
