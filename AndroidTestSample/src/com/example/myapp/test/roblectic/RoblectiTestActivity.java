package com.example.myapp.test.roblectic;

import android.app.Activity;
import android.os.Bundle;

import com.example.myapp.test.R;


/**
 * Created by xieningtao on 16-2-27.
 */
public class RoblectiTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roblectic_activity);

    }

    public String getTestStr(){
        return "hello";
    }
}
