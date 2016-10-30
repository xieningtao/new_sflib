package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.SFSample.R;
import com.sflib.CustomView.viewgroup.BaseLivePopAdapter;
import com.sflib.CustomView.viewgroup.LivePopView;

/**
 * Created by NetEase on 2016/10/14 0014.
 */
public class ActivityLivePopView extends BaseActivity {
    private LivePopView mLivePopView;
    private int mNumber=-1;
    private String imageUrl[] = {
            "http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
            "http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
            "http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"
    };
    private String content[] = {
            "aajfsdkjfksjk",
            "bbjflksjfks",
            "ccjskfjsdklfj"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_pop);
        mLivePopView = (LivePopView) findViewById(R.id.pop_view);
        mLivePopView.setAdapter(new LivePopAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("添加");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mNumber++;
        mLivePopView.doPush();
        return super.onOptionsItemSelected(item);
    }

    class LivePopAdapter extends BaseLivePopAdapter {

        @Override
        public View getView(View rootView,int position) {
            if (rootView == null) {
                rootView = LayoutInflater.from(ActivityLivePopView.this).inflate(R.layout.item_pop_view, null);
            }
            ImageView mPhotoIv = (ImageView) rootView.findViewById(R.id.photo_iv);
            ImageLoader.getInstance().displayImage(imageUrl[mNumber%3], mPhotoIv);
            TextView contentTv = (TextView) rootView.findViewById(R.id.comment_tv);
            contentTv.setText(content[mNumber%3]);
            return rootView;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
