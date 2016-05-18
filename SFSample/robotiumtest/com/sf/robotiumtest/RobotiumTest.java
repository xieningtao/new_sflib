//package com.sf.robotiumtest;
//
//import android.test.ActivityInstrumentationTestCase2;
//
//import com.robotium.solo.Solo;
//import com.sf.SFSample.home.HomeLevelActivity;
//
///**
// * Created by xieningtao on 15-9-19.
// */
//public class RobotiumTest extends ActivityInstrumentationTestCase2<HomeLevelActivity> {
//    private Solo mSolo;
//
//    public RobotiumTest(String pkg, Class<HomeLevelActivity> activityClass) {
//        super(pkg, activityClass);
//    }
//
//    public RobotiumTest(Class<HomeLevelActivity> activityClass) {
//        super(activityClass);
//    }
//
//    public RobotiumTest() {
//        super(HomeLevelActivity.class);
//    }
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        mSolo = new Solo(getInstrumentation(), getActivity());
//    }
//
//    public void testPull() {
//        mSolo.clickOnButton("pull");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        mSolo.goBack();
//        mSolo.clickOnButton("pull");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//        if (mSolo != null) {
//            mSolo.finishOpenedActivities();
//        }
//    }
//}
