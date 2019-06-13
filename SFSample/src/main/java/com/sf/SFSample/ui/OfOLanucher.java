package com.sf.SFSample.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.maxleap.GetCallback;
import com.maxleap.MLObject;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.exception.MLException;
import com.sf.SFSample.R;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFToast;

/**
 * Created by mac on 17/1/17.
 */

public class OfOLanucher extends BaseActivity {
    private static final int NOTIFICATION_FLAG = 1;

    public static final String BIKE_NUMBER = "bike_number";
    private EditText mBikeNumber;
    private TextView mBikePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luancher);
        mBikeNumber = (EditText) findViewById(R.id.bike_number_et);
        mBikePwd = (TextView) findViewById(R.id.bike_pwd_tv);
        findViewById(R.id.launch_ofo_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBikeNumber.getText())) {
                    SFToast.showToast("请输入ofo车牌号码");
                    return;
                }

                Intent intent = getPackageManager().getLaunchIntentForPackage("so.ofo.labofo");
                startActivity(intent);
                createNotifycation();
            }
        });
        findViewById(R.id.search_ofo_pw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBikeNumber.getText())) {
                    SFToast.showToast("请输入ofo车牌号码");
                    return;
                }
                String number = mBikeNumber.getText().toString();
                MLQuery ofoPwd = MLQuery.getQuery("BikePwd");
                ofoPwd.whereEqualTo("bikeNumber", number);
                MLQueryManager.getFirstInBackground(ofoPwd, new GetCallback() {
                    @Override
                    public void done(MLObject mlObject, MLException e) {
                        if (mlObject != null && !TextUtils.isEmpty(mlObject.getString("password"))) {
                            SFToast.showToast("成功获取密码");
                            mBikePwd.setText(mlObject.getString("password"));
                        } else {
                            SFToast.showToast("获取密码失败");
                        }
                    }
                });
            }
        });
    }

    private void createNotifycation() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ActivityScreenShot.class);
        intent.putExtra(BIKE_NUMBER, mBikeNumber.getText().toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 下面需兼容Android 2.x版本是的处理方式
        // Notification notify1 = new Notification(R.drawable.message,
        // "TickerText:" + "您有新短消息，请注意查收！", System.currentTimeMillis());
        Notification notify1 = new Notification();
        notify1.icon = R.drawable.app_icon;
        notify1.tickerText = "点击上传解锁密码";
        notify1.when = System.currentTimeMillis();
//        notify1.setLatestEventInfo(this, "保存密码",
//                "点击上传解锁密码", pendingIntent);
        notify1.number = 1;
        notify1.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
        // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示
        manager.notify(NOTIFICATION_FLAG, notify1);
    }
}
