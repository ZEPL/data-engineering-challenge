package com.jepl.utils;

import org.slf4j.*;

import java.io.*;
import java.util.*;

import javax.ws.rs.core.*;

import static javax.ws.rs.core.Response.Status.OK;

public class ResponseUtil {

    static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    public Response ok(Object obj) {
        if(obj instanceof List) {
            try {
                String jsonString = JsonUtil.writeValueAsString(obj);
                FileUtil.writeString("/tmp/todos.json", jsonString);
            } catch (IOException e) {
                logger.error("can't write json {}", e);
            }
        }

        return Response.status(OK).entity(obj).build();
    }
}
