package sf.com.unity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayerActivity;

/**
 * Created by NetEase on 2017/2/17 0017.
 */

public class ActivityUnityPlayerTest extends UnityPlayerActivity {
    private final String TAG=getClass().getName();
    private FrameLayout mUnityContainer;
    private Button mFirstBt;
    private Button mSecondBt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unity_tester);
        mUnityContainer= (FrameLayout) findViewById(R.id.unity_container_fl);
        mUnityContainer.addView(mUnityPlayer.getView());
        mFirstBt= (Button) findViewById(R.id.first_bt);
        mFirstBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOne();
            }
        });
        mSecondBt= (Button) findViewById(R.id.second_bt);
        mSecondBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSecond();
            }
        });

    }


    public void clickOne(){
        Log.i(TAG,"clickOne");
    }

    public void clickSecond(){
        Log.i(TAG,"clickSecond");
    }

    public void printLog(String tag,String content){
        Log.i(tag,content);
    }
}
