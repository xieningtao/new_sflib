package com.sflib.reflection.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.basesmartframe.basethread.ThreadHelp;
import com.basesmartframe.log.L;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by xieningtao on 15-10-24.
 */
class SFBridge {
    private static final String TAG = SFBridge.class.getClass().getName();
    private final Map<Integer, List<SFSlot>> mMessageSlotMap = new HashMap<>();
    private static Executor mRegisterThread = new BridgeThread("register_thread");
    private static Executor mWorkThread = new BridgeThread("background_thread");

    static class BridgeThread extends HandlerThread implements Executor {
        private Handler mHandler;

        public BridgeThread(String name) {
            super(name);
            init();
        }

        public BridgeThread(String name, int priority) {
            super(name, priority);
            init();
        }

        private void init() {
            start();
            Looper looper = getLooper();
            if (looper != null) {
                mHandler = new Handler(looper);
                if (looper.getThread() != null) {
                    Thread thread = looper.getThread();
                    L.info(TAG, "method->init in class BridgeThread,thread id: " + thread.getId() + " name: " + thread.getName());
                } else {
                    L.error(TAG, "method->init in class BridgeThread thread is null");
                }
            } else {
                L.error(TAG, "method->init in class BridgeThread,looper is null");
            }

        }

        public void execute(Runnable runnable) {
            mHandler.post(runnable);
        }
    }


    public void register(final Object object) {
        mRegisterThread.execute(new Runnable() {
            @Override
            public void run() {
                registerMethod(object);
            }
        });
    }

    public void unregister(final Object object) {
        mRegisterThread.execute(new Runnable() {
            @Override
            public void run() {
                unregisterMethod(object);
            }
        });
    }

    public void sendMsg(final Integer messageId, final Object... params) {
        mRegisterThread.execute(new Runnable() {
            @Override
            public void run() {
                sendMessage(messageId, params);
            }
        });
    }

    private void registerMethod(final Object object) {
        if (object == null) return;
        Class<?> instance = object.getClass();
        final Method methods[] = instance.getMethods();
        final ThreadId class_threadId = getThreadId(instance);
        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                final Method method = methods[i];
                if (method.isAnnotationPresent(SFIntegerMessage.class)) {
                    add(object, method, class_threadId);
                }
            }
        }
    }

    private ThreadId getThreadId(Class<?> instance) {
        if (instance != null && instance.isAnnotationPresent(SFIntegerMessage.class)) {
            SFIntegerMessage messageAnnotation = instance.getAnnotation(SFIntegerMessage.class);
            if (messageAnnotation != null) {
                return messageAnnotation.theadId();
            } else {
                return ThreadId.MainThread;
            }
        }
        return ThreadId.MainThread;
    }

    private void unregisterMethod(final Object object) {
        if (object == null) return;
        Class<?> instance = object.getClass();
        final Method methods[] = instance.getMethods();
        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                final Method method = methods[i];
                if (method.isAnnotationPresent(SFIntegerMessage.class)) {
                    remove(object, method);
                }
            }
        }
    }

    private void sendMessage(final Integer messageId, final Object... params) {
        final List<SFSlot> messageSlotList = getMessageSlotBy(messageId);
        if (messageSlotList.size() == 0) return;
        for (int i = 0; i < messageSlotList.size(); i++) {
            SFSlot slot = messageSlotList.get(i);
            invoke(slot, params);
        }
    }

    private void invoke(final SFSlot slot, final Object... params) {
        final Method method = slot.mMethod;
        final Object object = slot.mObject;
        final ThreadId threadId = slot.mThreadId;
        if (threadId == null || threadId == ThreadId.None) {
            invokeHelp(method, object, params);
        } else if (threadId == ThreadId.MainThread) {
            ThreadHelp.runInMain(new Runnable() {
                @Override
                public void run() {
                    invokeHelp(method, object, params);
                }
            });
        } else if (threadId == ThreadId.BackThread) {
            mWorkThread.execute(new Runnable() {
                @Override
                public void run() {
                    invokeHelp(method, object, params);
                }
            });
        }

    }

    private void invokeHelp(final Method method, final Object object, final Object... params) {
        try {
            method.invoke(object, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            L.error(TAG, "method->invoke,exception: " + e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            L.error(TAG, "method->invoke,exception: " + e.getMessage());
        }
    }

    private void add(final Object object, final Method method, final ThreadId pThreadId) {
        final SFIntegerMessage methodAnnotation = method.getAnnotation(SFIntegerMessage.class);
        final Class<?> parameter[] = method.getParameterTypes();
        final int messageId = methodAnnotation.messageId();
        ThreadId threadId = methodAnnotation.theadId();
        if (threadId == ThreadId.None) {
            threadId = pThreadId;
        }
        final List<SFSlot> messageSlotList = getMessageSlotBy(messageId);
        final SFSlot slot = new SFSlot(object, method, parameter, threadId);
        messageSlotList.add(slot);
    }

    private void remove(final Object object, final Method method) {
        if (object == null) return;
        final SFIntegerMessage methodAnnotation = method.getAnnotation(SFIntegerMessage.class);
        final int messageId = methodAnnotation.messageId();
        final List<SFSlot> messageSlotList = getMessageSlotBy(messageId);
        if (messageSlotList.size() == 0) return;
        Iterator<SFSlot> iterator = messageSlotList.iterator();
        while (iterator.hasNext()) {
            SFSlot slot = iterator.next();
            if (slot.mObject == object) {
                iterator.remove();
            }
        }
    }


    private List<SFSlot> getMessageSlotBy(final Integer messageId) {
        List<SFSlot> slotList = mMessageSlotMap.get(messageId);
        if (slotList == null) {
            slotList = new ArrayList<>();
            mMessageSlotMap.put(messageId, slotList);
        }
        return slotList;
    }

}
