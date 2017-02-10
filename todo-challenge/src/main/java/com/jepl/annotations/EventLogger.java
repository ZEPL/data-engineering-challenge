package com.jepl.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD)
public @interface EventLogger {}
