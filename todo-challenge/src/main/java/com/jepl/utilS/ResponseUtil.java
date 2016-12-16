package com.jepl.utils;

import com.jepl.resources.*;

import org.eclipse.jetty.http.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

import static javax.ws.rs.core.Response.Status.OK;

public class ResponseUtil {
    static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
    public static Response build(int statusCode, Object obj) {
        return Response.status(statusCode).entity(obj).build();
    }

    public static Response okBuild(Object obj) {
        if(obj instanceof List) {
            try {
                String jsonString = JsonUtil.writeValueAsString(TodoResource.todos);
                FileUtil.writeString("/tmp/todos.json", jsonString);
            } catch (IOException e) {
                logger.error("can't write json {}", e);
            }
        }

        return Response.status(OK).entity(obj).build();
    }

    public static Response errorBuild(int status, Object obj) {
        return Response.status(status).entity(obj).build();
    }

    public static void checkNull(Object o) {
        if(Objects.isNull(o)) {
            throw new NotFoundException();
        }
    }

}
