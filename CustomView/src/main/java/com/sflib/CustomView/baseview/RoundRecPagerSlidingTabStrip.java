/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sflib.CustomView.baseview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import com.sflib.CustomView.R;

public class RoundRecPagerSlidingTabStrip extends HorizontalScrollView {

    // @formatter:off
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    private final PageListener pageListener = new PageListener();
    // @formatter:on
    public OnPageChangeListener delegatePageListener;
    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;
    private LinearLayout tabsContainer;
    private ViewPager pager;
    private int tabCount;
    private int currentPosition = 0;
    private float currentPositionOffset = 0f;
    private Paint rectPaint;
    private Paint dividerPaint;
    private int indicatorColor = 0xFF666666;
    private int underlineColor = 0x1A000000;
    private int dividerColor = 0x1A000000;
    private boolean shouldExpand = false;
    private boolean textAllCaps = true;
    private int scrollOffset = 52;
    private int indicatorHeight = 8;
    private int underlineHeight = 2;
    private int dividerPadding = 12;
    private int tabPadding = 24;
    private int dividerWidth = 1;
    private int indicatorLeftRightPadding = 0;
    private int indicatorTopBottomPadding = 0;
    private int tabTextSize = 12;
    private float indicatorStrokeWidth = 1.0f;
    private float indicatorRectCorner = 1;
    private ColorStateList tabTextColor = ColorStateList.valueOf(0xFF666666);
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.NORMAL;
    private int lastScrollX = 0;
    private int tabBackgroundResId = R.color.background_tab;
    private Locale locale;

    public RoundRecPagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public RoundRecPagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRecPagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        indicatorLeftRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorLeftRightPadding, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

        // get system attrs (android:textSize and android:textColor)

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

        tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
        tabTextColor = a.getColorStateList(1);

        a.recycle();

        // get custom attrs

        a = context.obtainStyledAttributes(attrs, R.styleable.RoundRecPagerSlidingTabStrip);

        indicatorColor = a.getColor(R.styleable.RoundRecPagerSlidingTabStrip_cpstsIndicatorColor, indicatorColor);
        underlineColor = a.getColor(R.styleable.RoundRecPagerSlidingTabStrip_cpstsUnderlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.RoundRecPagerSlidingTabStrip_cpstsDividerColor, dividerColor);
        indicatorHeight = a.getDimensionPixelSize(R.styleable.RoundRecPagerSlidingTabStrip_cpstsIndicatorHeight, indicatorHeight);
        underlineHeight = a.getDimensionPixelSize(R.styleable.RoundRecPagerSlidingTabStrip_cpstsUnderlineHeight, underlineHeight);
        dividerPadding = a.getDimensionPixelSize(R.styleable.RoundRecPagerSlidingTabStrip_cpstsDividerPadding, dividerPadding);
        tabPadding = a.getDimensionPixelSize(R.styleable.RoundRecPagerSlidingTabStrip_cpstsTabPaddingLeftRight, tabPadding);
        tabBackgroundResId = a.getResourceId(R.styleable.RoundRecPagerSlidingTabStrip_cpstsTabBackground, tabBackgroundResId);
        shouldExpand = a.getBoolean(R.styleable.RoundRecPagerSlidingTabStrip_cpstsShouldExpand, shouldExpand);
        scrollOffset = a.getDimensionPixelSize(R.styleable.RoundRecPagerSlidingTabStrip_cpstsScrollOffset, scrollOffset);
        textAllCaps = a.getBoolean(R.styleable.RoundRecPagerSlidingTabStrip_cpstsTextAllCaps, textAllCaps);
        indicatorLeftRightPadding = a.getDimensionPixelSize(R.styleable.RoundRecPagerSlidingTabStrip_cpstsIndicatorLeftRightPadding, indicatorLeftRightPadding);
        indicatorTopBottomPadding = a.getDimensionPixelSize(R.styleable.RoundRecPagerSlidingTabStrip_cpstsIndicatorTopBottomPadding, indicatorTopBottomPadding);
        indicatorStrokeWidth = a.getFloat(R.styleable.RoundRecPagerSlidingTabStrip_cpstsIndicatorStrokeWidth, indicatorStrokeWidth);
        indicatorRectCorner = a.getFloat(R.styleable.RoundRecPagerSlidingTabStrip_cpstsIndicatorRectCorner, indicatorRectCorner);
        a.recycle();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(indicatorStrokeWidth);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.setOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {

        tabsContainer.removeAllViews();

        PagerAdapter adapter = pager.getAdapter();

        tabCount = adapter.getCount();

        for (int i = 0; i < tabCount; i++) {
            if (adapter instanceof TipIconTabProvider) {
                TipIconTabProvider provider = (TipIconTabProvider) adapter;
                addTipIconTextTab(i, adapter.getPageTitle(i).toString(), provider.getPageIconResId(i)
                        , provider.isTipShow(i), provider.getPageIconLocation(i));
            } else if (adapter instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) adapter).getPageIconResId(i));
            } else if (adapter instanceof CustomTabProvider) {
                CustomTabProvider ad = (CustomTabProvider) adapter;
                addCustomTab(i, ad.getCustomTabView(i), ad.getCustomTabWeight(i));
            } else {
                addTextTab(i, adapter.getPageTitle(i).toString());
            }
        }

        updateTabStyles();

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                getViewTreeObserver().removeGlobalOnLayoutListener(this);

                currentPosition = pager.getCurrentItem();
                scrollToChild(currentPosition, 0);
            }
        });
    }

    private void addTextTab(final int position, String title) {

        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();

        addTab(position, tab);
    }

    private void addTipIconTextTab(final int position, String title, int resId, boolean tipShow, int iconLocation) {
        View tab = inflate(getContext(), R.layout.tip_text_tab, null);
        TextView titleTV = (TextView) tab.findViewById(R.id.title);
        titleTV.setText(title);
        titleTV.setGravity(Gravity.CENTER);
        switch (iconLocation) {
            case IconTabProvider.KIcon_Left:
                titleTV.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
                break;
            case IconTabProvider.KIcon_Right:
                titleTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
                break;
            case TipIconTabProvider.KIcon_Bottom:
                titleTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resId);
                break;
            case TipIconTabProvider.KIcon_Top:
            default:
                titleTV.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
                break;
        }

        tab.findViewById(R.id.tip).setVisibility(tipShow ? View.VISIBLE : View.INVISIBLE);

        addTab(position, tab);
    }

    private void addIconTab(final int position, int resId) {

        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);

        addTab(position, tab);

    }

    private void addTab(final int position, View tab) {
        addTab(position, tab, -1.0f);
    }

    private void addCustomTab(final int position, View customTab, float weight) {
        addTab(position, customTab, weight);
    }

    private void addTab(final int position, View tab, float weight) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(position, Math.abs(pager.getCurrentItem() - position) == 1);
            }
        });

        tab.setPadding(tabPadding, 0, tabPadding, 0);
        if (-1.0f == weight) {
            tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
        } else {
            tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams
                    : new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, weight));
        }
    }

    private void updateTabStyles() {

        for (int i = 0; i < tabCount; i++) {

            View v = tabsContainer.getChildAt(i);

            v.setBackgroundResource(tabBackgroundResId);

            TextView tab;
            if (v instanceof TextView) {
                tab = (TextView) v;
            } else if (v instanceof RelativeLayout) {
                tab = (TextView) v.findViewById(R.id.title);
            } else {
                continue;
            }

            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
            tab.setTypeface(tabTypeface, tabTypefaceStyle);
            tab.setTextColor(tabTextColor);

            // setAllCaps() is only available from API 14, so the upper case is made manually if we are on a
            // pre-ICS-build
            if (textAllCaps) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    tab.setAllCaps(true);
                } else {
                    tab.setText(tab.getText().toString().toUpperCase(locale));
                }
            }
        }
    }

    private void scrollToChild(int position, int offset) {

        if (tabCount == 0) {
            return;
        }

        for (int i = 0; i < tabsContainer.getChildCount(); ++i) {
            tabsContainer.getChildAt(i).setSelected(i == position);
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabCount == 0) {
            return;
        }

        final int height = getHeight();

        // draw indicator line

        rectPaint.setColor(indicatorColor);

        // default: line below current tab
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();

        // if there is an offset, doRefresh interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
        }
        float indicator_top = getPaddingTop();
        float indicator_botom = currentTab.getHeight() + indicator_top;
        float indicator_left = lineLeft + getPaddingLeft();
//        L.info(this,"indicator_left: "+indicator_left+" lineLeft: "+lineLeft+" padding: "+currentTab.getPaddingLeft()+" currentPositionOffset: "+currentPositionOffset);
        RectF indicatorRec = new RectF(indicator_left - indicatorLeftRightPadding + tabPadding, indicator_top - indicatorTopBottomPadding, lineRight + indicatorLeftRightPadding - tabPadding, indicator_botom + indicatorTopBottomPadding);
        canvas.drawRoundRect(indicatorRec, indicatorRectCorner, indicatorRectCorner, rectPaint);

        // draw underline
        rectPaint.setColor(underlineColor);
        RectF rect = new RectF(0, height - underlineHeight, tabsContainer.getWidth(), height);
        canvas.drawRect(rect, rectPaint);
        // draw divider

        dividerPaint.setColor(dividerColor);
        for (int i = 0; i < tabCount - 1; i++) {
            View tab = tabsContainer.getChildAt(i);
            canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
        }
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getDividerPadding() {
        return dividerPadding;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public boolean getShouldExpand() {
        return shouldExpand;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColorStateList(resId);
        updateTabStyles();
    }

    public ColorStateList getTextColor() {
        return tabTextColor;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = ColorStateList.valueOf(textColor);
        updateTabStyles();
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    public int getTabBackground() {
        return tabBackgroundResId;
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    public interface IconTabProvider {
        public static final int KIcon_Top = 0;
        public static final int KIcon_Bottom = 1;
        public static final int KIcon_Left = 2;
        public static final int KIcon_Right = 3;

        public int getPageIconResId(int position);

        public int getPageIconLocation(int position);
    }

    public interface TipIconTabProvider extends IconTabProvider {
        public boolean isTipShow(int position);
    }

    public interface CustomTabProvider {
        public View getCustomTabView(int position);

        public float getCustomTabWeight(int position);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            currentPosition = position;
            currentPositionOffset = positionOffset;

            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

            invalidate();

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
        }
    }
}
