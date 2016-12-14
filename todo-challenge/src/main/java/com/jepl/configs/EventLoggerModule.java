package com.jepl.configs;

import com.google.inject.*;
import com.google.inject.matcher.*;

import com.jepl.annotations.*;
import com.jepl.brokers.*;

public class EventLoggerModule extends AbstractModule {
  protected void configure() {
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(EventLogger.class),
        new EventLoggerBlocker());
  }
}
