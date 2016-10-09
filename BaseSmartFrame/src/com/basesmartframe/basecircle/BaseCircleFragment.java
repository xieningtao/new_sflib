package com.basesmartframe.basecircle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.baseui.BasePullListFragment;

public abstract class BaseCircleFragment<T, N, C> extends
		BasePullListFragment<T> {

	public static interface CircleViewItemEvent {
		void onNineGrideViewItemClick(AdapterView<?> parent, View view,
				int groupPosition, int position, long id);

		void onCommentListViewItemClick(AdapterView<?> parent, View view,
				int groupPosition, int position, long id);
	}

	private CircleViewItemEvent itemEvent;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		itemEvent = createItemEvent();
	}

	@Override
	protected int[] getLayoutIds() {
		return getMainViewLayoutIds();
	}

	@Override
	protected void bindView(BaseAdapterHelper help, final int groupPosition,
			final T bean) {
		bindMainView(help, groupPosition, bean);
		int nine_gv_id = getNineGrideViewId();
		FixedGridView nine_gv = help.getView(nine_gv_id);
		nine_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (null != itemEvent) {
					itemEvent.onNineGrideViewItemClick(parent, view,
							groupPosition, position, id);
				}
			}
		});
		if (null == nine_gv.getTag()) {
			NineViewAdapte nineAdapter = new NineViewAdapte(groupPosition);
			nine_gv.setAdapter(nineAdapter);
			nine_gv.setTag(nineAdapter);
		} else {
			NineViewAdapte adapter = (BaseCircleFragment<T, N, C>.NineViewAdapte) nine_gv
					.getTag();
			adapter.setGroupPosition(groupPosition);
			adapter.notifyDataSetChanged();
		}
		int comment_lv_id = getCommentListViewId();
		FixedListView comment_lv = help.getView(comment_lv_id);
		comment_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (null != itemEvent) {
					itemEvent.onCommentListViewItemClick(parent, view,
							groupPosition, groupPosition, id);
				}
			}
		});
		if (null == comment_lv.getTag()) {
			CommentViewAdapter commentAdapter = new CommentViewAdapter(
					groupPosition);
			comment_lv.setAdapter(commentAdapter);
			comment_lv.setTag(commentAdapter);
		} else {
			CommentViewAdapter adapter = (BaseCircleFragment<T, N, C>.CommentViewAdapter) comment_lv
					.getTag();
			adapter.setGroupPosition(groupPosition);
			adapter.notifyDataSetChanged();
		}
	}

	class NineViewAdapte extends BaseAdapter {
		private final int layouts[];
		private int groupPos;

		public NineViewAdapte(int gp) {
			layouts = getNineGridViewLayoutIds();
			this.groupPos = gp;
		}

		public void setGroupPosition(int gp) {
			this.groupPos = gp;
		}

		@Override
		public int getCount() {
			return getNineGridViewCount(groupPos, getPullItem(groupPos));
		}

		@Override
		public Object getItem(int position) {
			return getNineGridItem(groupPos, position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return getNineViewType(position);
		}

		@Override
		public int getViewTypeCount() {
			return layouts == null ? 0 : layouts.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (null == convertView) {
				int resIndex = getItemViewType(position);
				convertView = LayoutInflater.from(getActivity()).inflate(
						layouts[resIndex], null);
			}
			BaseAdapterHelper helper = getAdaterHelp(convertView, position);
			bindNineGridView(helper, groupPos, position,
					getNineGridItem(groupPos, position));
			return convertView;
		}

	}

	class CommentViewAdapter extends BaseAdapter {
		private final int layouts[];
		private int groupPos;

		public CommentViewAdapter(int gp) {
			layouts = getCommentListviewLayoutIds();
			groupPos = gp;
		}

		public void setGroupPosition(int gp) {
			this.groupPos = gp;
		}

		@Override
		public int getCount() {
			return getCommentListViewCount(groupPos, getPullItem(groupPos));
		}

		@Override
		public Object getItem(int position) {
			return getCommentListItem(groupPos, position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return getCommentViewType(position);
		}

		@Override
		public int getViewTypeCount() {
			return layouts == null ? 0 : layouts.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (null == convertView) {
				int resIndex = getItemViewType(position);
				convertView = LayoutInflater.from(getActivity()).inflate(
						layouts[resIndex], null);
			}
			BaseAdapterHelper helper = getAdaterHelp(convertView, position);
			bindCommentListView(helper, groupPos, position,
					getCommentListItem(groupPos, position));
			return convertView;
		}

	}

	protected CircleViewItemEvent createItemEvent() {
		return null;
	}

	@Override
	protected int getViewType(int position) {
		return getMainViewType(position);
	}

	protected C getCommentListItem(int groupPos, int childPos) {
		return getCommentListItem(childPos, getPullItem(groupPos));
	}

	protected N getNineGridItem(int groupPos, int childPos) {
		return getNineGrideItem(childPos, getPullItem(groupPos));
	}

	protected int getMainViewType(int position) {
		return 0;
	}

	protected int getNineViewType(int position) {
		return 0;
	}

	protected int getCommentViewType(int position) {
		return 0;
	}


	abstract protected void bindNineGridView(BaseAdapterHelper help,
			int GroupPosition, int childPosition, N bean);

	abstract protected void bindCommentListView(BaseAdapterHelper help,
			int GroupPosition, int childPosition, C bean);

	abstract protected void bindMainView(BaseAdapterHelper help,
			int GroupPosition, T bean);

	abstract protected int getNineGridViewCount(int groupPos, T bean);

	abstract protected int getCommentListViewCount(int groupPos, T bean);

	abstract protected int[] getMainViewLayoutIds();

	abstract protected int[] getNineGridViewLayoutIds();

	abstract protected int[] getCommentListviewLayoutIds();

	abstract protected int getNineGrideViewId();

	abstract protected int getCommentListViewId();

	abstract protected N getNineGrideItem(int childPos, T bean);

	abstract protected C getCommentListItem(int childPos, T bean);

}
