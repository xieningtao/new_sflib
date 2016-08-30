package com.sf.Roboletic.customshadow;

import com.sf.Roboletic.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.internal.Shadow;
import org.robolectric.internal.ShadowExtractor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by NetEase on 2016/8/17 0017.
 */
@RunWith(CustomShadowTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CustomShadowExample {

    @Test
    @Config(shadows = {ShadowLoginUtil.class})
    public void testShadow() throws Exception {
        LoginUtil loginUtil=new LoginUtil();
        ShadowLoginUtil shadowLoginUtil = (ShadowLoginUtil) ShadowExtractor.extract(loginUtil);
//        ShadowLoginUtil shadowLoginUtil=new ShadowLoginUtil();
//        LoginUtil loginUtil=new LoginUtil();
        shadowLoginUtil.setOnline(true);
        System.out.println("true,result: "+loginUtil.isOnline());
        assertTrue(loginUtil.isOnline());


        shadowLoginUtil.setOnline(false);
        System.out.println("false,result: "+loginUtil.isOnline());
        assertFalse(loginUtil.isOnline());

    }
}
