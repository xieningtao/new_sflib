LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := jni_test
LOCAL_SRC_FILES := jni_test.c

include $(BUILD_SHARED_LIBRARY)