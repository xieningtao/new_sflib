package com.sf.SFSample.babymedical;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;
import com.sf.SFSample.SFApp;
import com.sf.SFSample.UserInfoBeanDao;
import com.sf.loglib.L;
import com.sf.utils.baseutil.UnitHelp;
import com.sflib.CustomView.baseview.AutoCompleteTextClearDroidView;
import com.sflib.CustomView.baseview.EditTextClearDroidView;
import com.sf.utils.ThreadHelp;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 16/9/3.
 */
public class ActivityLogin extends BaseActivity {

    private Button mLoginBt;
    private AutoCompleteTextClearDroidView mUserName;
    private EditTextClearDroidView mPwd;

    private UserNameAdapter mUserNameAdapter;
    private AutoCompleteTextView mAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_medical_login);
        initView();
    }

    private void initView() {
        mLoginBt = (Button) findViewById(R.id.login_bt);
        mUserName = (AutoCompleteTextClearDroidView) findViewById(R.id.login_atv);
        mPwd = (EditTextClearDroidView) findViewById(R.id.pwd_cdv);

        mLoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = mUserName.getAutoCompleteTextView().getText().toString();

                ThreadHelp.runInSingleBackThread(new Runnable() {
                    @Override
                    public void run() {
                        final UserInfoBean userInfoBean = new UserInfoBean();
                        userInfoBean.setUserName(userName);
                        List<UserInfoBean> userInfoBeanList = SFApp.getInstance().getDaoSession().getUserInfoBeanDao()
                                .queryBuilder()
                                .where(UserInfoBeanDao.Properties.UserName.eq(userName)).list();
                        if (userInfoBeanList == null || userInfoBeanList.isEmpty()) {
                            SFApp.getInstance().getDaoSession().getUserInfoBeanDao().save(userInfoBean);
                        }

                    }
                }, 0);

            }
        });

        mUserName.getAutoCompleteTextView().setHint(R.string.user_name_input_hint);
        mPwd.getEditText().setHint(R.string.pwd_input_hint);

        mUserName.getAutoCompleteTextView().addTextChangedListener(mTextWatcher);
        mPwd.getEditText().addTextChangedListener(mTextWatcher);

        mAutoCompleteTextView = mUserName.getAutoCompleteTextView();
        mUserNameAdapter = new UserNameAdapter(this, R.layout.baby_medical_user_name_item);
        mAutoCompleteTextView.setThreshold(1);
        mAutoCompleteTextView.setDropDownBackgroundResource(R.drawable.baby_medical_user_name_pop_bg);
        mAutoCompleteTextView.setDropDownAnchor(R.id.login_atv);
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                mAutoCompleteTextView.setDropDownWidth(mUserName.getWidth());
            }
        }, 500);

        mAutoCompleteTextView.setAdapter(mUserNameAdapter);
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAutoCompleteTextView.dismissDropDown();
            }
        });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            updateLoginState();
        }
    };

    private void updateLoginState() {
        if (!TextUtils.isEmpty(mUserName.getAutoCompleteTextView().getText()) && !TextUtils.isEmpty(mPwd.getEditText().getText())) {
            mLoginBt.setEnabled(true);
        } else {
            mLoginBt.setEnabled(false);
        }
    }

    class UserNameAdapter extends BaseAdapter implements Filterable {
        private LayoutInflater mLayoutInflater;
        private int mRootViewId;
        private List<UserInfoBean> mUserInfoBeanList = new ArrayList<>();

        public UserNameAdapter(Context context, int rootViewId) {
            mLayoutInflater = LayoutInflater.from(context);
            mRootViewId = rootViewId;
        }

        @Override
        public int getCount() {
            int size = mUserInfoBeanList.size();
            return size;
        }

        @Override
        public Object getItem(int position) {
            return mUserInfoBeanList.get(position).getUserName();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(mRootViewId, null);
            }
            TextView userNameTv = (TextView) convertView.findViewById(R.id.user_name_tv);
            UserInfoBean userInfoBean = mUserInfoBeanList.get(position);
            if (userInfoBean != null) {
                userNameTv.setText(userInfoBean.getUserName());
            }
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                        List<UserInfoBean> userInfoBeanList =SFApp.getInstance().getDaoSession().getUserInfoBeanDao().queryBuilder()
                        .where(UserInfoBeanDao.Properties.UserName.like(String.valueOf(constraint))).list();
                        FilterResults results = new FilterResults();
                        results.values = userInfoBeanList;
                        return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    mUserInfoBeanList.clear();
                    if (results != null && results.values != null) {
                        List<UserInfoBean> userInfoBeanList = (List<UserInfoBean>) results.values;
                        mUserInfoBeanList.addAll(userInfoBeanList);
                    }
                    if (mUserInfoBeanList.size() > 3) {
                        mAutoCompleteTextView.setDropDownHeight(UnitHelp.dip2px(ActivityLogin.this, 120));
                    } else {
                        mAutoCompleteTextView.setDropDownHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                    notifyDataSetChanged();
                }
            };
        }
    }
}
