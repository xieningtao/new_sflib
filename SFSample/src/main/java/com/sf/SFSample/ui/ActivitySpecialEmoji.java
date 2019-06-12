package com.sf.SFSample.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by NetEase on 2016/7/29 0029.
 */
public class ActivitySpecialEmoji extends BaseActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_emoji);

        final TextView emojiTv = (TextView) findViewById(R.id.emoji_tv);

//        emojiTv.setText("\ud83d\ude03");
        String _emojiStr1 = "\uD83D";
        String _emojiStr2 = "\uDE2D";
        String _emojiStr = _emojiStr1 + _emojiStr2;
        emojiTv.setText(_emojiStr);

        final EditText emojiEt = (EditText) findViewById(R.id.emoji_et);

        findViewById(R.id.emoji_convert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emojiStr = emojiEt.getText().toString();
                String another_emoji=unicodeToString(emojiStr);
                emojiTv.setText(another_emoji);
            }
        });

        final Button emojiEtRev= (Button) findViewById(R.id.emoji_convert_res);
        emojiEtRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emojiStr = emojiEt.getText().toString();
                String another_emoji=stringToUnicode(emojiStr);
                emojiTv.setText(another_emoji);
            }
        });

//        emojiTv.setText("\ud83d\ude2d");
//        emojiTv.setText("\ud83d\ude2d");//\ud83d\ude03

    }

    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);


        int ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), ch + "");

        }

        return str;

    }

    /**
     * 把中文字符串转换为十六进制Unicode编码字符串
     *
     * @param s
     *            中文字符串
     * @return
     */
    public static String stringToUnicode(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            if (ch > 255)
                str += "\\u" + Integer.toHexString(ch);
            else
                str += "\\" + Integer.toHexString(ch);
        }
        return str;
    }
}
