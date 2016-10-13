package com.sf.SFSample.tiantu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.basesmartframe.baseui.BaseFragment;
import com.basesmartframe.pickphoto.ImageBean;
import com.basesmartframe.pickphoto.PickPhotosPreviewFragment;
import com.basesmartframe.request.SFHttpGsonHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.SFSample.R;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.SFRequest;
import com.sf.utils.baseutil.UnitHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/9/18 0018.
 */
public class FragmentTianTuList extends BaseFragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mTianTuView;
    private TianTuAdapter mTianTuAdapter;
    private List<TianTuImageBeans.TianTuImageBean> mTianTuImageBeanList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tiantu_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.tian_tu_sr);
        mTianTuView = (RecyclerView) view.findViewById(R.id.tian_tu_rlv);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mTianTuView.setLayoutManager(staggeredGridLayoutManager);
        mTianTuView.addItemDecoration(new TianTuDecorition(UnitHelp.dip2px(getActivity(), 10)));
        mTianTuAdapter = new TianTuAdapter(getActivity());
        mTianTuView.setAdapter(mTianTuAdapter);
        loadData();
    }

    private void loadData() {
        String url = "https://www.baidu.com/";
        SFRequest request = new SFRequest(url, MethodType.GET) {
            @Override
            public Class getClassType() {
                return TianTuImageBeans.class;
            }
        };
        SFHttpGsonHandler sfHttpGsonHandler = new SFHttpGsonHandler(request, new SFHttpStringCallback<TianTuImageBeans>() {
            private String url[] = {
                    "http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
                    "http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
                    "http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"
                    , "http://img1.imgtn.bdimg.com/it/u=1967061078,642942007&fm=21&gp=0.jpg",
                    "http://img2.imgtn.bdimg.com/it/u=2403748927,3196049645&fm=21&gp=0.jpg",
                    "http://pic.yesky.com/uploadImages/2015/165/47/H4NF287N0833.jpg",
                    "http://www.bz55.com/uploads/allimg/150527/140-15052G44205.jpg"
            };

            @Override
            public void onSuccess(SFRequest request, TianTuImageBeans g) {
                for (int i = 0; i < url.length; i++) {
                    TianTuImageBeans.TianTuImageBean tianTuImageBean = new TianTuImageBeans.TianTuImageBean();
                    tianTuImageBean.setOriginUrl(url[i]);
                    mTianTuImageBeanList.add(tianTuImageBean);
                }
                mTianTuAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(SFRequest request, Exception e) {
                for (int i = 0; i < url.length; i++) {
                    TianTuImageBeans.TianTuImageBean tianTuImageBean = new TianTuImageBeans.TianTuImageBean();
                    tianTuImageBean.setOriginUrl(url[i]);
                    mTianTuImageBeanList.add(tianTuImageBean);
                }
                mTianTuAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        sfHttpGsonHandler.start();
    }

    private ArrayList<ImageBean> tianTuImageList2ImageBean() {
        ArrayList<ImageBean> imageBeanList = new ArrayList<>();
        for (TianTuImageBeans.TianTuImageBean imageBean : mTianTuImageBeanList) {
            ImageBean newImageBean = new ImageBean();
            newImageBean.setPath(imageBean.getOriginUrl());
            imageBeanList.add(newImageBean);
        }
        return imageBeanList;
    }

    class TianTuAdapter extends RecyclerView.Adapter<TianTuHolder> {
        private LayoutInflater mLayoutInflater;

        public TianTuAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public TianTuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = mLayoutInflater.inflate(R.layout.item_tian_tu, null);
            return new TianTuHolder(rootView);
        }

        @Override
        public void onBindViewHolder(TianTuHolder holder, final int position) {
            TianTuImageBeans.TianTuImageBean imageBean = mTianTuImageBeanList.get(position);
            ImageLoader.getInstance().displayImage(imageBean.getOriginUrl(), holder.mImageView);
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ActivityPhotoPreview.class);
                    intent.putExtra(PickPhotosPreviewFragment.INDEX, position);
                    intent.putExtra(ActivityPhotoPreview.IMAGE_BEAN_LIST, tianTuImageList2ImageBean());
                    FragmentTianTuList.this.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTianTuImageBeanList.size();
        }
    }

    class TianTuHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public TianTuHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.tian_tu_iv);
        }
    }

    class TianTuDecorition extends RecyclerView.ItemDecoration {
        private int mSpace;

        public TianTuDecorition(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = 5;
            outRect.right = 5;
            outRect.bottom = mSpace;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mSpace;
            }
        }

    }
}
