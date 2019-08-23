package com.sf.SFSample.ui;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.basesmartframe.anim.animation.recycleview.ViewUtils;
import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/9/25.
 */

public class ActivityPopView extends BaseActivity {

    private RecyclerView mPopViewRl;
    private PopViewAdapter mPopViewAdapter;

    private List<PopItemBean> mPopItemBeans = new ArrayList<>();

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
        setContentView(R.layout.activity_pop_view);
        init();
    }

    private void init() {
        for (int i = 0; i < 3; i++) {
            PopItemBean popItemBean = new PopItemBean();
            popItemBean.setmContent(content[i % 3]);
            popItemBean.setmPhotoUrl(imageUrl[i % 3]);
            mPopItemBeans.add(popItemBean);
        }
        mPopViewRl = findViewById(R.id.popview_rl);
        mPopViewRl.setLayoutManager(new LinearLayoutManager(this));
//        mPopViewRl.setItemAnimator(new PackageAnimator(new ScaleIn(),new ScaleOut()));
        ViewUtils.init(this);
        mPopViewAdapter = new PopViewAdapter();
        mPopViewRl.setAdapter(mPopViewAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("添加");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PopItemBean popItemBean = new PopItemBean();
        popItemBean.setmContent(content[0]);
        popItemBean.setmPhotoUrl(imageUrl[0]);
        mPopItemBeans.add(popItemBean);
        mPopViewAdapter.notifyItemInserted(mPopItemBeans.size()-1);
        return super.onOptionsItemSelected(item);
    }

    class PopViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentTv;
        public final ImageView mPhotoIv;

        public PopViewHolder(View itemView) {
            super(itemView);
            mContentTv = itemView.findViewById(R.id.comment_tv);
            mPhotoIv = itemView.findViewById(R.id.photo_iv);
        }
    }

    class PopViewAdapter extends RecyclerView.Adapter<PopViewHolder> {

        @Override
        public PopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(ActivityPopView.this).inflate(R.layout.item_pop_view, null);
            return new PopViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(PopViewHolder holder, int position) {
            PopItemBean popItemBean=mPopItemBeans.get(position);
//            ImageLoader.getInstance().displayImage(popItemBean.getmPhotoUrl(),holder.mPhotoIv);
            holder.mContentTv.setText(popItemBean.getmContent());
        }

        @Override
        public int getItemCount() {
            return mPopItemBeans.size();
        }
    }

    class PopItemBean {
        private String mPhotoUrl;
        private String mContent;

        public PopItemBean() {

        }

        public PopItemBean(String mPhotoUrl, String mContent) {
            this.mPhotoUrl = mPhotoUrl;
            this.mContent = mContent;
        }

        public String getmPhotoUrl() {
            return mPhotoUrl;
        }

        public void setmPhotoUrl(String mPhotoUrl) {
            this.mPhotoUrl = mPhotoUrl;
        }

        public String getmContent() {
            return mContent;
        }

        public void setmContent(String mContent) {
            this.mContent = mContent;
        }
    }
}
