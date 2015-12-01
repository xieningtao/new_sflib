package com.sflib.reflection.core;

import java.lang.reflect.Method;

/**
 * Created by xieningtao on 15-10-24.
 */
public class SFSlot {
    public final Object mObject;
    public final Method mMethod;
    public final ThreadId mThreadId;
    public final Class<?> mParameter[];

    public SFSlot(Object object, Method method, Class<?> parameter[], ThreadId threadId) {
        this.mObject = object;
        mMethod = method;
        mThreadId = threadId;
        mParameter = parameter;
    }
}
