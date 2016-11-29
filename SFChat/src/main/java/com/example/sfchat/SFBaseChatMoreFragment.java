package com.example.sfchat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseFragment;
import com.sf.loglib.L;
import com.sf.loglib.file.SFFileCreationUtil;
import com.sf.utils.baseutil.DateFormatHelp;

/**
 * Created by NetEase on 2016/8/11 0011.
 */
public class SFBaseChatMoreFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private void dispatchCommand(int position) {
        switch (position) {
            case 0:
                SFCameraCommand cameraCommand = (SFCameraCommand) ChatMoreItem.CAMERA.getChatCommand();
                if (cameraCommand == null) {
                    cameraCommand = new SFCameraCommand(SFBaseChatMoreFragment.this);
                    ChatMoreItem.CAMERA.setChatCommand(cameraCommand);
                }
                cameraCommand.setImagePath(SFFileCreationUtil.createNameByTimeFormat(DateFormatHelp._YYYYMMDDHHMMSS_FORMAT));
                cameraCommand.executeCommand();
                break;
            case 1:
                SFCustomAlbumCommand albumCommand = (SFCustomAlbumCommand) ChatMoreItem.ALBUM.getChatCommand();
                if (albumCommand == null) {
                    albumCommand = new SFCustomAlbumCommand(SFBaseChatMoreFragment.this);
                    ChatMoreItem.ALBUM.setChatCommand(albumCommand);
                }
                albumCommand.executeCommand();
                break;
            case 2:
                SFBaiduMapCommand mapCommand = (SFBaiduMapCommand) ChatMoreItem.LOCATION.getChatCommand();
                if (mapCommand == null) {
                    mapCommand = new SFBaiduMapCommand(SFBaseChatMoreFragment.this);
                    ChatMoreItem.LOCATION.setChatCommand(mapCommand);
                }
                mapCommand.executeCommand();
                break;
        }
    }

    public enum ChatMoreItem {
        CAMERA(R.drawable.module_msgsender_guider_camera, R.string.camera),
        ALBUM(R.drawable.module_msgsender_guider_albuml, R.string.album),
        LOCATION(R.drawable.module_msgsender_guider_location, R.string.location);
        public final int mDrawableId;
        public final int mTextId;

        private SFBaseChatCommand mChatCommand;

        ChatMoreItem(int drawableId, int textId) {
            this.mDrawableId = drawableId;
            this.mTextId = textId;
        }

        public SFBaseChatCommand getChatCommand() {
            return mChatCommand;
        }

        public void setChatCommand(SFBaseChatCommand chatCommand) {
            mChatCommand = chatCommand;
        }
    }

    private GridView mMoreGv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sf_chat_more, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        mMoreGv = (GridView) view.findViewById(R.id.chat_more_gv);
        mMoreGv.setAdapter(new ChatMoreAdapter());
        mMoreGv.setOnItemClickListener(this);
    }

    class ChatMoreAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ChatMoreItem.values().length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.sf_chat_more_item, null);
            }
            ChatMoreItem chatMoreItem = ChatMoreItem.values()[position];
            TextView headTv = (TextView) convertView.findViewById(R.id.chat_more_tv);
            headTv.setText(chatMoreItem.mTextId);
            ImageView moreIb = (ImageView) convertView.findViewById(R.id.chat_more_ib);
            moreIb.setImageResource(chatMoreItem.mDrawableId);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchCommand(position);
                }
            });
            return convertView;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

        } else {

        }
        switch (requestCode) {
            case SFBaseChatCommand.ALBUM_REQUEST:
                if (data != null && data.getData() != null) {
                    Uri albumUri = data.getData();
                    L.info(TAG, TAG + ".onActivityResult,albumUri: " + albumUri);
                }
                break;
            case SFBaseChatCommand.CAMERA_REQUEST:
                SFCameraCommand cameraCommand = (SFCameraCommand) ChatMoreItem.CAMERA.getChatCommand();
                Uri cameraUri = cameraCommand.getPhotoUri();
                L.info(TAG, TAG + ".onActivityResult,cameraUri: " + cameraUri);
                if (cameraUri != null) {

                }
                break;
            case SFBaseChatCommand.LOCATION_REQUEST:

            break;
        }
    }
}
