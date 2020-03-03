package com.sf.SFSample.home;

import android.os.Bundle;
import android.util.Pair;

import com.sf.SFSample.chat.ActivitySFChat;
import com.sf.SFSample.increaseupdate.IncreaseUpdateActivity;
import com.sf.SFSample.ui.ActivityBlur;
import com.sf.SFSample.ui.ActivityLivePopView;
import com.sf.SFSample.ui.ActivityNewCircle;
import com.sf.SFSample.ui.ActivityPopView;
import com.sf.SFSample.ui.ActivitySpecialEmoji;
import com.sf.SFSample.ui.ActivityUpgrade;
import com.sf.SFSample.ui.AdbShellActivity;
import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.CircleTest;
import com.sf.SFSample.ui.DecencoderActivity;
import com.sf.SFSample.ui.RoundDrawableActivity;
import com.sf.SFSample.ui.UMengShareActivity;
import com.sf.SFSample.utils.FileWRActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeLevelActivity extends BaseLevelActivity {
    @Override
    protected List<Pair<String, Class>> getActivities() {
        List<Pair<String, Class>> activities = new ArrayList<>();
        activities.add( new Pair<String, Class>("circle", CircleTest.class));
        activities.add( new Pair<String, Class>("roundBitmap", RoundDrawableActivity.class));
        activities.add( new Pair<String, Class>("customView", CustomViewLevelActivity.class));
        activities.add( new Pair<String, Class>("adb shell", AdbShellActivity.class));
        activities.add( new Pair<String, Class>("DecencoderActivity", DecencoderActivity.class));

        activities.add( new Pair<String, Class>("UMengShareActivity", UMengShareActivity.class));
        activities.add( new Pair<String, Class>("ActivitySelected", ActivitySelected.class));
        activities.add( new Pair<String, Class>("ActivityUpgrade", ActivityUpgrade.class));
        activities.add( new Pair<String, Class>("ActivitySpecialEmoji", ActivitySpecialEmoji.class));
        activities.add( new Pair<String, Class>("ActivityChat", ActivitySFChat.class));
        activities.add( new Pair<String, Class>("ActivityBaiduFamily", ActivityBaiduFamily.class));
        activities.add( new Pair<String, Class>("ActivityNewCircle", ActivityNewCircle.class));
        activities.add( new Pair<String, Class>("ActivityPopView", ActivityPopView.class));
        activities.add( new Pair<String, Class>("ActivityLivePopView", ActivityLivePopView.class));
        activities.add( new Pair<String, Class>("ActivityBlur", ActivityBlur.class));
        activities.add( new Pair<String, Class>("IncreaseUpdate", IncreaseUpdateActivity.class));
        activities.add( new Pair<String, Class>("SDKLNewFeatureLevelActivity", SDKLNewFeatureLevelActivity.class));
        activities.add( new Pair<String, Class>("FileWRActivity", FileWRActivity.class));
        return activities;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
