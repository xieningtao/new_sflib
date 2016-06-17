package com.sf.Roboletic;

import android.content.Intent;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;


/**
 * Created by xieningtao on 15-9-19.
 */
    @RunWith(RobolectricTestRunner.class)
    public class RoboleticActivityTest {

        @Test
        public void clickingLogin_shouldStartLoginActivity() {
            RoboleticActivity activity = Robolectric.setupActivity(RoboleticActivity.class);
            activity.findViewById(R.id.click_bt).performClick();
            Intent expectedIntent = new Intent(activity, LoginActivity.class);
            Intent nextStartedActivity=Shadows.shadowOf(activity).getNextStartedActivity();
            Assert.assertTrue(nextStartedActivity.equals(expectedIntent));
        }
}
