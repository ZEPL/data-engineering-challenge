package com.jepl.utils;

import com.jepl.resources.*;

import org.eclipse.jetty.http.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;

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
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream("/tmp/todos.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(TodoResource.todos);
                out.close();
                fileOut.close();
            } catch (FileNotFoundException e) {
                logger.error("file not found exception. {}", e);
            } catch (IOException e) {
                logger.error("io exception. {}", e);
            }


        }

        return Response.status(OK).entity(obj).build();
    }

    public static Response errorBuild(int status, Object obj) {
        return Response.status(status).entity(obj).build();
    }
}
