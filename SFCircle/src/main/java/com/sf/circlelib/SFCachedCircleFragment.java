package com.sf.circlelib;

import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.request.SFHttpGsonHandler;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.SFRequest;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SFCachedCircleFragment extends SFCircleFragment<SFContent,SFImage,SFComment> {

    private void doRequest(){
        String url="https://www.baidu.com/";
        SFRequest request=new SFRequest(url, MethodType.GET) {
            @Override
            public Class getClassType() {
                return SFContent.class;
            }
        };
        SFHttpGsonHandler handler=new SFHttpGsonHandler(request, new SFHttpStringCallback<SFContent>() {

            @Override
            public void onSuccess(SFRequest request, SFContent g) {

            }

            @Override
            public void onFailed(SFRequest request, Exception e) {

            }
        });
        handler.start();
    }
    @Override
    protected boolean onRefresh() {
            doRequest();
        return true;
    }

    @Override
    protected boolean onLoadMore() {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
