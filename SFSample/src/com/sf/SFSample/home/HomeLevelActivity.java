package com.sf.SFSample.home;

import android.os.Bundle;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.widget.VideoView;

import com.example.sfchat.media.MediaPlayManager;
import com.example.sfchat.media.MediaRecordManager;
import com.example.sfchat.media.NewAudioRecorderManager;
import com.sf.SFSample.emoji.EmojiActivity;
import com.sf.SFSample.chat.ActivitySFChat;
import com.sf.SFSample.ui.ActivitySpecialEmoji;
import com.sf.SFSample.ui.ActivityUpgrade;
import com.sf.SFSample.ui.AdbShellActivity;
import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.BitMapSizeActivity;
import com.sf.SFSample.ui.CircleTest;
import com.sf.SFSample.ui.DecencoderActivity;
import com.sf.SFSample.ui.FragmentTabActivity;
import com.sf.SFSample.ui.GestureListViewActiviy;
import com.sf.SFSample.ui.GestureTest;
import com.sf.SFSample.ui.PullListActivity;
import com.sf.SFSample.ui.ReflectionActivity;
import com.sf.SFSample.ui.RoundDrawableActivity;
import com.sf.SFSample.ui.UMengShareActivity;
import com.sf.SFSample.ui.VideoShowTest;
import com.sf.SFSample.ui.ViewPagerTest;
import com.sf.SFSample.ui.XPinListViewActivity;

public class HomeLevelActivity extends BaseLevelActivity {

    View mView;

    VideoView mVideoView;

    @Override
    protected SparseArray<Pair<String, Class>> getActivities() {
        SparseArray<Pair<String, Class>> activities = new SparseArray<>();
        activities.put(0, new Pair<String, Class>("pull", PullListActivity.class));
        activities.put(1, new Pair<String, Class>("viewPager", ViewPagerTest.class));
        activities.put(2, new Pair<String, Class>("circle", CircleTest.class));
        activities.put(3, new Pair<String, Class>("roundBitmap", RoundDrawableActivity.class));
        activities.put(4, new Pair<String, Class>("gesture", GestureTest.class));
        activities.put(5, new Pair<String, Class>("gesturePullList", GestureListViewActiviy.class));
        activities.put(6, new Pair<String, Class>("videoview", VideoShowTest.class));
        activities.put(7, new Pair<String, Class>("customView", CustomViewLevelActivity.class));
        activities.put(8, new Pair<String, Class>("reflection", ReflectionActivity.class));
        activities.put(9, new Pair<String, Class>("opengl", OpenGLLevelActivity.class));
        activities.put(10, new Pair<String, Class>("tv", TVLevelActivity.class));
        activities.put(11, new Pair<String, Class>("adb shell", AdbShellActivity.class));
        activities.put(12, new Pair<String, Class>("XPinListView", XPinListViewActivity.class));
        activities.put(13, new Pair<String, Class>("DecencoderActivity", DecencoderActivity.class));
        activities.put(14, new Pair<String, Class>("UMengShareActivity", UMengShareActivity.class));
        activities.put(15, new Pair<String, Class>("FragmentTabActivity", FragmentTabActivity.class));
        activities.put(16, new Pair<String, Class>("EmojiActivity", EmojiActivity.class));
        activities.put(17, new Pair<String, Class>("BitMapSizeActivity", BitMapSizeActivity.class));
        activities.put(18, new Pair<String, Class>("ActivitySelected", ActivitySelected.class));
        activities.put(19, new Pair<String, Class>("ActivityUpgrade", ActivityUpgrade.class));
        activities.put(20, new Pair<String, Class>("ActivitySpecialEmoji", ActivitySpecialEmoji.class));
        activities.put(21, new Pair<String, Class>("ActivityChat", ActivitySFChat.class));
        activities.put(22, new Pair<String, Class>("ActivityBaiduFamily", ActivityBaiduFamily.class));

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
