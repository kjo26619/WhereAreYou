package com.escape.way.config.logging;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogEntry {

    // Log Level
    LogLevel value() default LogLevel.INFO;

    // Execution Period
    ChronoUnit unit() default ChronoUnit.SECONDS;

    // Show Method Args
    boolean showArgs() default false;

    // Show Method Result
    boolean showResult() default false;

    // Execution Time Flag
    boolean showExecutionTime() default true;
}
