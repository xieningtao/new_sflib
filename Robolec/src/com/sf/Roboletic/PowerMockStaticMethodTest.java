package com.sf.Roboletic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by NetEase on 2016/8/16 0016.
 */
@RunWith(PowerMockRunner.class)
//@Config(constants = BuildConfig.class, sdk = 21)
//@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest(PowerMockStaticMethod.class)
public class PowerMockStaticMethodTest {

    @Test
    public void testGetDataStaticMethod() {
        PowerMockito.mockStatic(PowerMockStaticMethod.class);
//        try {
//            PowerMockito.doReturn(50).when(PowerMockStaticMethod.class,"geta");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.print("exception: "+e);
//        }

        int result = PowerMockStaticMethod.geta();
        System.out.println("result: " + result);
        //verify start
        PowerMockito.verifyStatic(Mockito.times(1));
        PowerMockStaticMethod.geta();
        //verify end
    }
}
