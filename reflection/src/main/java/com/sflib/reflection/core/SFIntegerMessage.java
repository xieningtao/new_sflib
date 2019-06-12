package com.sflib.reflection.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xieningtao on 15-10-24.
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SFIntegerMessage {
    public int messageId() default -1;

    public ThreadId theadId() default ThreadId.None;

}
