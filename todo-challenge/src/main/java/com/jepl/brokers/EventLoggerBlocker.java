package com.jepl.brokers;

import com.jepl.annotations.*;

import org.aopalliance.intercept.*;
import org.slf4j.*;

public class EventLoggerBlocker implements MethodInterceptor {
  static final Logger logger = LoggerFactory.getLogger(EventLoggerBlocker.class);

  public Object invoke(MethodInvocation invocation) throws Throwable {
      Object[] arguments = invocation.getArguments();
      StringBuffer sb = new StringBuffer();
      if(arguments != null & arguments.length > 0) {
          for (int i =0; i < arguments.length; i++) {
              sb.append(String.format("\n                   argument %d : %s", (i + 1), arguments[i]));
          }
      }

      logger.info("[EVENT INFO] {} {}", invocation.getMethod().getName(), sb.toString());
      return invocation.proceed();
  }
}
