package com.example.androidtv;

import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.baseui.BaseFragment;
import com.basesmartframe.log.L;
import com.example.androidtv.focus.FocusHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 15-12-23.
 */
public abstract class ZoomBaseGridFragment extends BaseFragment {
    protected final String TAG = getClass().getName();
    private ObjectAdapter.DataObserver mDataObserver;
    private RecyclerView.Adapter mAdapter;
    private ObjectAdapter mObjectAdapter;
    private List<Presenter> mPresenters = new ArrayList<>();

    protected abstract RecyclerView getRecyclerView();

    public void setAdapter(RecyclerView.Adapter adapter) {
        getRecyclerView().setAdapter(adapter);
    }

    public void setAdapter(ObjectAdapter adapter) {
        if (mObjectAdapter != null && mDataObserver != null) {
            mObjectAdapter.unregisterObserver(mDataObserver);
        }
        mObjectAdapter = adapter;
        mAdapter = new SimpleAdapter();
        if (mObjectAdapter != null) {
            mObjectAdapter.registerObserver(mDataObserver);
        }
        getRecyclerView().setAdapter(mAdapter);
    }

    private void init() {
        mDataObserver = new ObjectAdapter.DataObserver() {
            @Override
            public void onChanged() {
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                if (mAdapter != null) {
                    mAdapter.notifyItemRangeChanged(positionStart, itemCount);
                }
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                if (mAdapter != null) {
                    mAdapter.notifyItemRangeChanged(positionStart, itemCount);
                }
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                if (mAdapter != null) {
                    mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
                }
            }
        };
    }

    class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public SimpleAdapter() {
            init();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            Presenter presenter = mPresenters.get(viewType);
            if (presenter != null) {
                Presenter.ViewHolder pViewHolder = presenter.onCreateViewHolder(viewGroup);
                RecyclerView.ViewHolder recyclerViewHolder = new ZoomBaseGridFragment.SimpleViewHolder(pViewHolder.view, presenter, pViewHolder);
                final FocusHelper.BrowseItemFocusHighlight highlight = new FocusHelper.BrowseItemFocusHighlight(Boolean.FALSE);
                highlight.onInitializeView(recyclerViewHolder.itemView);
                recyclerViewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        L.info(TAG, "method->onFocusChange,view: " + v + " hasFocus: " + hasFocus);
                        highlight.onItemFocused(v, hasFocus);
                    }
                });

                return recyclerViewHolder;
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                ZoomBaseGridFragment.SimpleViewHolder simpleViewHolder = (ZoomBaseGridFragment.SimpleViewHolder) viewHolder;
                Object object = mObjectAdapter.get(i);
                Presenter.ViewHolder pViewHolder = simpleViewHolder.mViewHolder;
                simpleViewHolder.mPresenter.onBindViewHolder(pViewHolder, object);
            }
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            if (holder != null) {
                ZoomBaseGridFragment.SimpleViewHolder simpleViewHolder = (ZoomBaseGridFragment.SimpleViewHolder) holder;
                Presenter presenter = simpleViewHolder.mPresenter;
                presenter.onUnbindViewHolder(simpleViewHolder.mViewHolder);
            }
        }

        @Override
        public int getItemCount() {
            return mObjectAdapter.size();
        }

        @Override
        public int getItemViewType(int position) {
            PresenterSelector presenterSelector = mObjectAdapter.getPresenterSelector();
            Object item = mObjectAdapter.get(position);
            Presenter presenter = presenterSelector.getPresenter(item);
            int type = mPresenters.indexOf(presenter);
            if (type < 0) {
                mPresenters.add(presenter);
                type = mPresenters.indexOf(presenter);
            }

            return type;
        }
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final Presenter mPresenter;
        public final Presenter.ViewHolder mViewHolder;

        public SimpleViewHolder(View itemView, Presenter presenter, Presenter.ViewHolder viewHolder) {
            super(itemView);
            this.mPresenter = presenter;
            this.mViewHolder = viewHolder;
        }
    }
}
