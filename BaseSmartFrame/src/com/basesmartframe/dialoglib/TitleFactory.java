package com.basesmartframe.dialoglib;

import com.basesmartframe.R;
import com.sf.loglib.L;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by xieningtao on 15-4-17.
 */
public class TitleFactory {

    private TextView left_tv;
    private TextView title_tv;
    private ImageButton right_ib;

    public static interface TitleEvent {
        void onLeftClick(View view);

        void onRightClick(View view);
    }

    public static class TitleBuilder {
        private TextView left_tv;
        private TextView title_tv;
        private ImageButton right_ib;
        private Context context;

        public TitleBuilder(Context context, View view) {
            if (null == view) {
                L.error(this, "view is null");
            } else if (null == context) {
                L.error(this, "context is null");
            } else {
                left_tv = (TextView) view.findViewById(R.id.pop_left_tv);
                title_tv = (TextView) view.findViewById(R.id.pop_title_tv);
                right_ib = (ImageButton) view.findViewById(R.id.pop_right_ib);
                this.context = context;
            }
        }

        public TitleBuilder setTitle(String title) {
            if (null != title) {
                title_tv.setText(title);
                title_tv.setTypeface(title_tv.getTypeface(), Typeface.BOLD);
            }
            return this;
        }

        public TitleBuilder setTitle(int title) {
            title_tv.setText(title);
            title_tv.setTypeface(title_tv.getTypeface(), Typeface.BOLD);
            return this;
        }

        public TitleBuilder setLeft(String left) {
            if (null != left) {
                left_tv.setText(left);
                left_tv.setTypeface(left_tv.getTypeface(), Typeface.BOLD);
            }
            return this;
        }

        public TitleBuilder setLeft(int left) {
            left_tv.setText(left);
            left_tv.setTypeface(left_tv.getTypeface(), Typeface.BOLD);
            return this;
        }

        public TitleBuilder setLeftDrawble(int leftDrawble) {
            Drawable drawable = context.getResources().getDrawable(leftDrawble);
            setLeftDrawble(drawable);
            return this;
        }

        public TitleBuilder setLeftDrawble(Drawable drawable) {
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                left_tv.setCompoundDrawables(drawable, null, null, null);
            }
            return this;
        }

        public TitleBuilder setTitleListener(final TitleEvent event) {
            if (null != event) {
                left_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        event.onLeftClick(view);
                    }
                });

                right_ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        event.onRightClick(view);
                    }
                });
            }
            return this;
        }

        public TitleFactory build() {
            if (title_tv == null) return null;
            return new TitleFactory(left_tv, title_tv, right_ib);
        }
    }

    private TitleFactory(TextView left_tv, TextView title_tv, ImageButton right_ib) {
        this.left_tv = left_tv;
        this.title_tv = title_tv;
        this.right_ib = right_ib;
    }

    public TextView getLeft_tv() {
        return left_tv;
    }


    public TextView getTitle_tv() {
        return title_tv;
    }


    public ImageButton getRight_ib() {
        return right_ib;
    }

}
