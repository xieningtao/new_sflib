package com.sf.yysdkdemo;

import android.app.Activity;
import android.os.Bundle;

import com.sf.yysdkdemo.videoplayer.YYVideoView;

public class YYSdkLivingActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
//    private ChannelModule mChannelModule = new ChannelModule();
//    private ChannelRequestInfo mRequestInfo;

    private YYVideoView mYYVideoView;
    private EmptyGLSurfaceView mEmptyGLSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        mYYVideoView = (YYVideoView) findViewById(R.id.yyvideo);

        mEmptyGLSurfaceView= (EmptyGLSurfaceView) findViewById(R.id.empty_glsurfaceview);
        init();
    }

    private void init() {
//        Intent intent = getIntent();
//        Detail.TVDetailListBean bean = (Detail.TVDetailListBean) intent.getSerializableExtra("channel");
//        mRequestInfo = new ChannelRequestInfo.ChannelRequestInfoBuild().setGameId(bean.getiGameId())
//                .setKickContext("")
//                .setSid(bean.getlChannelId())
//                .setSubSid(bean.getlSubchannel())
//                .setVideoRate(0).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mChannelModule.joinChannelRequest(mRequestInfo);
//        mYYVideoView.onStart();
        mEmptyGLSurfaceView.onResume();
//        DebugHelper.exitDelay(6 * 1000, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mYYVideoView.onStop();
        mEmptyGLSurfaceView.onPause();
//        mChannelModule.leaveChannelRequest();
    }
}
