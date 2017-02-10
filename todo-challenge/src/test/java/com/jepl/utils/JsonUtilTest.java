package com.jepl.utils;

import com.fasterxml.jackson.core.type.*;

import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class JsonUtilTest {

    @Test
    public void testReadJsonString() throws IOException {
        Map map = JsonUtil.readValue("{\"key\":\"value\"}", new TypeReference<HashMap>() { });
        assertEquals("value", map.get("key"));
    }

    @Test
    public void testWriteJsonString() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        String resultJson = JsonUtil.writeValueAsString(map);
        assertEquals("{\"key\":\"value\"}", resultJson);
    }
}
