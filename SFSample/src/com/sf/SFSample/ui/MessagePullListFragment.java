package com.sf.SFSample.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.example.sfchat.BaseChatMessageShowFragment;
import com.example.sfchat.SFChatMessageId;
import com.example.sfchat.item.MsgType;
import com.example.sfchat.item.chatbean.SFAudio;
import com.example.sfchat.item.chatbean.SFGif;
import com.example.sfchat.item.chatbean.SFLocationBean;
import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.chatbean.SFPic;
import com.example.sfchat.item.chatbean.SFTxt;
import com.example.sfchat.item.chatbean.SFUserInfo;
import com.google.gson.Gson;
import com.sf.utils.baseutil.UnitHelp;
import com.sflib.reflection.core.SFIntegerMessage;
import com.sflib.reflection.core.ThreadHelp;
import com.sflib.reflection.core.ThreadId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/8/10 0010.
 */
public class MessagePullListFragment extends BaseChatMessageShowFragment {

    private List<SFMsg> sfMsgList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPullToRefreshListView().setDividerPadding(UnitHelp.dip2px(getActivity(), 10));
    }

    @Override
    protected boolean onRefresh() {
        sfMsgList.clear();
        sfMsgList.add(createMsg(true, MsgType.txt_type, ""));
        sfMsgList.add(createMsg(false, MsgType.location_type, ""));
        sfMsgList.add(createMsg(true, MsgType.photo_type, ""));
        sfMsgList.add(createMsg(true, MsgType.audio_type, ""));
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                finishRefreshOrLoading(sfMsgList, false);
            }
        }, 500);
        return true;
    }

    @Override
    protected boolean onLoadMore() {

        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private SFMsg createMsg(boolean fromMe, int type, String extra) {
        SFMsg sfMsg = new SFMsg();
        sfMsg.setType(type);
        sfMsg.setFromMe(fromMe);
        sfMsg.setUserInfo(createUserInfo());
        Gson gson = new Gson();
        if (type == MsgType.audio_type) {
            sfMsg.setContent(gson.toJson(createAudio()));
        } else if (type == MsgType.gif_type) {
            sfMsg.setContent(gson.toJson(createGif(extra)));
        } else if (type == MsgType.location_type) {
            sfMsg.setContent(gson.toJson(createLocationBean()));
        } else if (type == MsgType.photo_type) {
            sfMsg.setContent(gson.toJson(createPic()));
        } else if (type == MsgType.txt_type) {
            sfMsg.setContent(gson.toJson(createTxt()));
        } else if (type == MsgType.paper_type) {
            sfMsg.setContent(gson.toJson(createPaper(extra)));
        }
        return sfMsg;
    }

    private SFLocationBean createLocationBean() {
        SFLocationBean locationBean = new SFLocationBean();
        locationBean.setAddress("天河区博览路大厦");
        return locationBean;
    }

    private SFPic createPic() {
        SFPic pic = new SFPic();
        pic.setUrl("http://img2.imgtn.bdimg.com/it/u=1423839187,2315004481&fm=23&gp=0.jpg");
        return pic;
    }

    private SFTxt createTxt() {
        SFTxt txt = new SFTxt();
        txt.setContent("测试内容");
        return txt;
    }

    private SFTxt createEmojiTxt() {
        SFTxt txt = new SFTxt();
        txt.setContent("测试emoji");
        return txt;
    }

    private SFGif createGif(String path) {
        SFGif gif = new SFGif();
        gif.setUrl(path);
        return gif;
    }

    private SFPic createPaper(String path) {
        SFPic pic = new SFPic();
        pic.setUrl(path);
        return pic;
    }

    private SFAudio createAudio() {
        SFAudio audio = new SFAudio();
        audio.setMilliseconds(10 * 60 * 1000);
        audio.setUrl("http://res.webftp.bbs.hnol.net/lovegege/NANA/2014/07/01.nan/09.mp3");
        return audio;
    }

    private SFUserInfo createUserInfo() {
        SFUserInfo userInfo = new SFUserInfo();
        userInfo.setUrl("http://img1.imgtn.bdimg.com/it/u=1671017973,3798717908&fm=23&gp=0.jpg");
        userInfo.setName("测试一号");
        return userInfo;
    }

    @SFIntegerMessage(messageId = SFChatMessageId.ADD_MSG, theadId = ThreadId.MainThread)
    public void onMessageAdded(String path) {
        if (TextUtils.isEmpty(path)) return;
        int index = path.lastIndexOf(".");
        String postFix = path.substring(index+1, path.length());
        if ("gif".equals(postFix)) {
            sfMsgList.add(createMsg(true, MsgType.gif_type, path));
        } else {
            sfMsgList.add(createMsg(false, MsgType.paper_type, path));
        }
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                finishRefreshOrLoading(sfMsgList, false);
            }
        }, 500);
    }
}
