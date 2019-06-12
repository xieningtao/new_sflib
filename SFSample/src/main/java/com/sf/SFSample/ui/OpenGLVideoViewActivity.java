/*
 * Copyright (C) 2011 The Android Open Source Project
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
package com.sf.SFSample.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.sf.SFSample.R;
import com.sflib.openGL.OpenGLVideo;
import com.sflib.openGL.OpenGLVideoView;

public class OpenGLVideoViewActivity extends Activity {

    private OpenGLVideoView mGLView;
    private RadioGroup mScaleType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);
        mGLView = (OpenGLVideoView) findViewById(R.id.gl_sf);
        mScaleType = (RadioGroup) findViewById(R.id.scale_type);
        mScaleType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.scale_fit_rb) {
                    mGLView.updateVideoViewScaleType(OpenGLVideo.FILL_FIT);
                } else if (checkedId == R.id.scale_full_rb) {
                    mGLView.updateVideoViewScaleType(OpenGLVideo.FULL_SCREEN);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGLView.changeConfiguration(true);
        } else {
            mGLView.changeConfiguration(false);
        }
    }
}