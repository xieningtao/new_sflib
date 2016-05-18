package com.example.myapp.test;


import android.os.Build;

import com.example.myapp.test.roblectic.RoblectiTestActivity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


/**
 * Created by xieningtao on 15-9-19.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "./AndroidManifest.xml",sdk = Build.VERSION_CODES.JELLY_BEAN_MR2
        ,resourceDir = "./res")

public class RoboleticTest {
    @Test
    public void testMainActivity() throws Exception{
        RoblectiTestActivity activity = Robolectric.setupActivity(RoblectiTestActivity.class);
        Assert.assertTrue(activity.getTestStr().equals("hello0"));
    }

}
