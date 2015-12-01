package com.sflib.reflection.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xieningtao on 15-10-24.
 */
class SFBridge {

    private final Map<Integer, List<SFSlot>> mMessageSlotMap = new HashMap<>();

    public void registerMethod(final Object object) {
        if (object == null) return;
        Class<?> instance = object.getClass();
        final Method methods[] = instance.getMethods();
        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                final Method method = methods[i];
                if (method.isAnnotationPresent(SFIntegerMessage.class)) {
                    add(object, method);
                }
            }
        }
    }

    public void unregisterMethod(final Object object) {
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

    public void sendMessage(final Integer messageId, final Object... params) {
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
        try {
            method.invoke(object, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void add(final Object object, final Method method) {
        final SFIntegerMessage methodAnnotation = method.getAnnotation(SFIntegerMessage.class);
        final Class<?> parameter[] = method.getParameterTypes();
        final int messageId = methodAnnotation.messageId();
        final ThreadId threadId = methodAnnotation.theadId();
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
