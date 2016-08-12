package com.example.sfchat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.basesmartframe.baseui.BaseFragment;
import com.sf.utils.baseutil.SystemUIHelp;
import com.sflib.reflection.core.ThreadHelp;
import com.umeng.socialize.editorpage.KeyboardListenRelativeLayout;

/**
 * Created by NetEase on 2016/8/10 0010.
 */
abstract public class SFBaseChatFragment extends BaseFragment {

    private ImageView mSmileIv, mGuider;
    private View mExpressionContainer;

    private Fragment mExpressionFragment;
    private Fragment mGuiderFragment;

    private EditText mInputEt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sf_chat_main, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    abstract protected Fragment createExpressionFragment();

    abstract protected Fragment createGuiderFragment();

    private void init(View view) {
        mSmileIv = (ImageView) view.findViewById(R.id.edit_smile);
        mGuider = (ImageView) view.findViewById(R.id.guider);
        mExpressionContainer = view.findViewById(R.id.expression_container);
        mInputEt= (EditText) view.findViewById(R.id.input_et);
        mExpressionContainer.setVisibility(View.GONE);
        final FragmentManager fragmentManager = getFragmentManager();
        mInputEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpressionContainer.setVisibility(View.GONE);
                SystemUIHelp.showSoftKeyboard(getActivity(),mInputEt);
                mInputEt.requestFocus();
            }
        });
        mSmileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUIHelp.hideSoftKeyboard(getActivity(),mInputEt);
                showExpressionContainer();
                if(mGuiderFragment!=null&&mGuiderFragment.isVisible()){
                    fragmentManager.beginTransaction().hide(mGuiderFragment).commit();
                }
                if (mExpressionFragment == null) {
                    mExpressionFragment = createExpressionFragment();
                    if (mExpressionFragment != null) {
                        fragmentManager.beginTransaction().add(R.id.expression_container, mExpressionFragment).commit();
                    }
                } else if (mExpressionFragment.isHidden()) {
                    fragmentManager.beginTransaction().show(mExpressionFragment).commit();
                }

            }
        });

        mGuider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUIHelp.hideSoftKeyboard(getActivity(),mInputEt);
                showExpressionContainer();
                if(mExpressionFragment!=null&&mExpressionFragment.isVisible()){
                    fragmentManager.beginTransaction().hide(mExpressionFragment).commit();
                }
                if (mGuiderFragment == null) {
                    mGuiderFragment = createGuiderFragment();
                    if (mGuiderFragment != null) {
                        fragmentManager.beginTransaction().add(R.id.expression_container, mGuiderFragment).commit();
                    }
                } else if (mGuiderFragment.isHidden()) {
                    fragmentManager.beginTransaction().show(mGuiderFragment).commit();
                }
            }
        });
    }

    private void showExpressionContainer() {
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                mExpressionContainer.setVisibility(View.VISIBLE);
            }
        },100);
    }
}
