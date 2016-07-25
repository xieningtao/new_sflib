package com.sf.Roboletic;

import com.nostra13.universalimageloader.utils.L;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

/**
 * Created by NetEase on 2016/7/21 0021.
 */
@RunWith(PowerMockRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
public class PowerMockLoginTest {

    private final String TAG = getClass().getName();

//    @Rule
//    public PowerMockRule rule = new PowerMockRule();

//    @Test
//    @PrepareForTest(PowerMockStaticMethod.class)
//    public void testStatic() {
////        PowerMockito.mockStatic(PowerMockStaticMethod.class);
////        PowerMockito.when(PowerMockStaticMethod.geta()).thenReturn(5);
//
//        PowerMockito.spy(PowerMockStaticMethod.class);
//        PowerMockito.when(PowerMockStaticMethod.geta()).thenReturn(21);
////        PowerMockito.verifyStatic();
////        Mockito.when(PowerMockStaticMethod.geta()).thenReturn(30);
//
////        try {
////            PowerMockito.doReturn(9).when(PowerMockStaticMethod.class, "geta");
////        } catch (Exception e) {
////            e.printStackTrace();
////            System.out.print("exception: "+e);
////        }
//
//        int result = PowerMockStaticMethod.geta();
////        L.i(TAG,TAG+".testStatic result: "+result);
//        System.out.print("result; " + result);
//    }

    @Test
    @PrepareForTest(TestMethod.class)
    public void testInnerMethod() {
        TestMethod testMethod = new TestMethod();
        TestMethod mockTestMethod=PowerMockito.spy(testMethod);
//        PowerMockito.doAnswer(new Answer<String>() {
//            @Override
//            public String answer(InvocationOnMock invocation) throws Throwable {
//                System.out.print("InvocationOnMock");
//                return null;
//            }
//        }).when(mockTestMethod).b();


//        PowerMockito.verify(mockTestMethod).b();
        try {
//            Method methods[]=testMethod.getClass().getDeclaredMethods();
//            for(int i=0;i<methods.length;i++){
//                System.out.println("method name: "+methods[i].getName());
//            }
            PowerMockito.when(mockTestMethod,"b").thenThrow(new NullPointerException("wrong method"));
            mockTestMethod.c(false);
            PowerMockito.verifyPrivate(mockTestMethod).invoke("a");
//            Mockito.verify(mockTestMethod).c(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception: "+e);
        }

    }
}
