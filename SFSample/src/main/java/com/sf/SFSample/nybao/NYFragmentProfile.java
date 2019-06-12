package com.sf.SFSample.nybao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.baseui.BaseFragment;
import com.sf.SFSample.R;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYFragmentProfile extends BaseFragment {
    public final int LOGIN_REQUEST_CODE = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ny_fragment_profile, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.personal_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NYLoginActivity.class);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LOGIN_REQUEST_CODE:
                    break;
            }
        }
    }
}
