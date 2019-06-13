package com.sf.SFSample.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.SFSample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2017/5/19 0019.
 */

public class CardActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private final String[] imgUrls = {
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=989281481,1942027673&fm=23&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2639842433,461542691&fm=23&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=659133392,2446997191&fm=23&gp=0.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapter adapter = new RecycleViewAdapter(this);
        List<HolderBean> holderBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            holderBeanList.add(new HolderBean(imgUrls[i % imgUrls.length], "fsfsafsaimag" + i));
        }
        adapter.setHolderBeenList(holderBeanList);
        mRecyclerView.setAdapter(adapter);
    }

    class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewHolder> {
        private final LayoutInflater mLayoutInflater;
        private List<HolderBean> mHolderBeenList = new ArrayList<>();

        public RecycleViewAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = mLayoutInflater.inflate(R.layout.card_view_item, parent,false);
            return new RecycleViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(RecycleViewHolder holder, int position) {
            HolderBean holderBean = mHolderBeenList.get(position);
            ImageLoader.getInstance().displayImage(holderBean.url, holder.mPicIv);
            holder.mInfoTv.setText(holderBean.txt);
        }

        @Override
        public int getItemCount() {
            return mHolderBeenList.size();
        }

        public void setHolderBeenList(List<HolderBean> holderBeenList) {
            if (holderBeenList == null || holderBeenList.size() == 0) {
                return;
            }
            mHolderBeenList.addAll(holderBeenList);
        }
    }

    class RecycleViewHolder extends RecyclerView.ViewHolder {
        public final CardView mCardView;
        public final TextView mInfoTv;
        public final ImageView mPicIv;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mInfoTv = (TextView) itemView.findViewById(R.id.info_text);
            mPicIv = (ImageView) itemView.findViewById(R.id.pic_iv);
        }
    }

    class HolderBean {
        public final String url;
        public final String txt;

        public HolderBean(String url, String txt) {
            this.url = url;
            this.txt = txt;
        }
    }
}
