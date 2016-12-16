package com.jepl.utils;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;


public class JsonUtil {

    private static ObjectMapper mapper = getMapper();


    public static <T> T readValue(String json, TypeReference valueTypeRef) throws IOException {
        return getMapper().readValue(json, valueTypeRef);
    }


    public static String writeValueAsString(Object obj) throws IOException {
        return getMapper().writeValueAsString(obj);
    }

    public static ObjectMapper getMapper() {
        if(mapper == null) {
            mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
        return mapper;
    }
}
