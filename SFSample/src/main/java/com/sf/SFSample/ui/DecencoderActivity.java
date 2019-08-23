package com.sf.SFSample.ui;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.utils.baseutil.RsaUtil;
import com.sf.loglib.L;
import com.sf.SFSample.R;

import java.io.UnsupportedEncodingException;


/**
 * Created by xieningtao on 16-5-17.
 */
public class DecencoderActivity extends BaseActivity implements View.OnClickListener {

    private Button mEncoder, mDecoder;
    private TextView mAfterEncoder, mAfterDecoder;
    private EditText mInput;

    private RadioGroup mCiper;

    private String mPublicKey = RsaUtil.PUBLIC_STANDARD_KEY;
    private String mPrivateKey = RsaUtil.PRIVATE_STANDARD_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decencoder_layout);
        init();
    }

    private void init() {
        mEncoder = findViewById(R.id.encoder);
        mDecoder = findViewById(R.id.decoder);

        mEncoder.setOnClickListener(this);
        mDecoder.setOnClickListener(this);

        mAfterEncoder = findViewById(R.id.after_encoder);
        mAfterDecoder = findViewById(R.id.after_decoder);

        mAfterDecoder.setOnClickListener(this);
        mAfterEncoder.setOnClickListener(this);

        mInput = findViewById(R.id.input_et);

        mCiper = findViewById(R.id.ciper_rg);
        mCiper.check(R.id.standar);
        mCiper.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.standar) {
                    mPrivateKey = RsaUtil.PRIVATE_STANDARD_KEY;
                    mPublicKey = RsaUtil.PUBLIC_STANDARD_KEY;
                } else if (checkedId == R.id.custom) {
                    mPrivateKey = RsaUtil.PRIVATE_KEY;
                    mPublicKey = RsaUtil.PUBLIC_KEY;
                }
                mAfterDecoder.setText("");
                mAfterEncoder.setText("");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.encoder:
                encoderContent();
                break;
            case R.id.decoder:
                decoderContent();
                break;
        }
    }

    private void encoderContent() {
        String content = mInput.getText().toString();
        byte contentData[] = new byte[0];
        try {
            contentData = content.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            L.error(TAG, "method->encoderContent,exception: " + e.getMessage());
        }
        byte encodeData[] = RsaUtil.encodeData(contentData, mPublicKey);
        if (encodeData != null) {
            String encode_content = Base64.encodeToString(encodeData, Base64.DEFAULT);
            mAfterEncoder.setText(encode_content);
        } else {
            L.error(TAG, "method->encoderContent, encodeData is null");
        }
    }

    private void decoderContent() {
        String content = mAfterEncoder.getText().toString();
        byte contentData[] = Base64.decode(content, Base64.DEFAULT);
        byte decoderData[] = RsaUtil.decodeData(contentData, mPrivateKey);
        try {
            if (decoderData != null) {
                String decoderStr = new String(decoderData, "utf-8");
                mAfterDecoder.setText(decoderStr);
            } else {
                L.error(TAG, "method->decoderContent decoderData is null");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            L.error(TAG, "method->decoderContent,exception: " + e.getMessage());
        }
    }
}
