//
//  jni_test.c
//  jnitest
//
//  Created by xieningtao on 15-12-5.
//  Copyright (c) 2015年 xieningtao. All rights reserved.
//

#include <jni.h>
#include <string.h>

JNIEXPORT jint JNICALL Java_com_example_SFJNILib_HelloActivity_getHello
(JNIEnv *jni, jobject object){
    
    const char *str="hello";
    jstring str_utf=(*jni)->NewStringUTF(jni,str);
    
    long str_legh=strlen(str_utf);
    
    jclass activity_class=(*jni)->FindClass(jni,"com/example/SFJNILib/HelloActivity");

    jmethodID getStr_id=(*jni)->GetMethodID(jni,activity_class,"getStr","()Ljava/lang/String;");

    jobject value=(*jni)->CallObjectMethod(jni,object,getStr_id);

    jsize length=(*jni)->GetStringLength(jni,value);

    return length;
}

