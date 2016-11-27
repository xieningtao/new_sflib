package com.example.sfchat.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sfchat.R;
import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.holder.AudioViewHolder;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.item.holder.GiftViewHolder;
import com.example.sfchat.item.holder.LocationViewHolder;
import com.example.sfchat.item.holder.PhotoViewHolder;
import com.example.sfchat.item.holder.TxtViewHolder;
import com.example.sfchat.item.view.BaseChatItemView;
import com.example.sfchat.item.view.ChatItemViewFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/11/27.
 */

public class BaseChatAdapter extends BaseAdapter {

    private List<SFMsg> msgList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public BaseChatAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private ChatViewType getViewType(int ordinal) {
        return ChatViewType.values()[ordinal];
    }

    @Override
    public int getItemViewType(int position) {
        SFMsg sfMsg = msgList.get(position);
        int type = sfMsg.getType();
        boolean fromMe = sfMsg.isFromMe();
        switch (type) {
            case MsgType.audio_type:
                if (fromMe) {
                    return ChatViewType.MSG_RIGHT_AUDIO.ordinal();
                } else {
                    return ChatViewType.MSG_LEFT_AUDIO.ordinal();
                }
            case MsgType.gif_type:
                if (fromMe) {
                    return ChatViewType.MSG_RIGHT_GIF.ordinal();
                } else {
                    return ChatViewType.MSG_LEFT_GIF.ordinal();
                }
            case MsgType.location_type:
                if (fromMe) {
                    return ChatViewType.MSG_RIGHT_LOCATION.ordinal();
                } else {
                    return ChatViewType.MSG_LEFT_LOCATION.ordinal();
                }
            case MsgType.photo_type:
                if (fromMe) {
                    return ChatViewType.MSG_RIGHT_PHOTO.ordinal();
                } else {
                    return ChatViewType.MSG_LEFT_PHOTO.ordinal();
                }
            case MsgType.txt_type:
                if (fromMe) {
                    return ChatViewType.MSG_RIGHT_TXT.ordinal();
                } else {
                    return ChatViewType.MSG_LEFT_TXT.ordinal();
                }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return ChatViewType.values().length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseChatItemView<SFMsg> chatItemView = null;
        BaseChatHolder chatHolder = null;
        HolderAndItemView holderAndItemView = null;
        if (convertView != null) {
            holderAndItemView = (HolderAndItemView) convertView.getTag();
            if (holderAndItemView != null) {
                boolean fromMe = msgList.get(position).isFromMe();
                if (fromMe == holderAndItemView.isRight) {
                    chatItemView = holderAndItemView.chatItemView;
                    chatHolder = holderAndItemView.chatHolder;
                }
            }
        }
        if (convertView == null || chatItemView == null) {
            ChatViewType viewType = getViewType(getItemViewType(position));
            boolean fromMe = msgList.get(position).isFromMe();
            switch (viewType) {
                case MSG_LEFT_AUDIO:
                case MSG_RIGHT_AUDIO:
                    if (fromMe) {
                        convertView = mLayoutInflater.inflate(R.layout.msg_right_audio_item, null);
                    } else {
                        convertView = mLayoutInflater.inflate(R.layout.msg_left_audio_item, null);
                    }
                    chatItemView = ChatItemViewFactory.createAudioItemView(mContext);
                    chatHolder = new AudioViewHolder(convertView);
                    break;
                case MSG_LEFT_GIF:
                case MSG_RIGHT_GIF:
                    if (fromMe) {
                        convertView = mLayoutInflater.inflate(R.layout.msg_right_gif_item, null);
                    } else {
                        convertView = mLayoutInflater.inflate(R.layout.msg_left_gif_item, null);
                    }
                    chatItemView = ChatItemViewFactory.createGifItemView(mContext);
                    chatHolder = new GiftViewHolder(convertView);
                    break;
                case MSG_LEFT_TXT:
                case MSG_RIGHT_TXT:
                    if (fromMe) {
                        convertView = mLayoutInflater.inflate(R.layout.msg_right_txt_item, null);
                    } else {
                        convertView = mLayoutInflater.inflate(R.layout.msg_left_txt_item, null);
                    }
                    chatItemView = ChatItemViewFactory.createTxtItemView(mContext);
                    chatHolder = new TxtViewHolder(convertView);
                    break;
                case MSG_LEFT_PHOTO:
                case MSG_RIGHT_PHOTO:
                    if (fromMe) {
                        convertView = mLayoutInflater.inflate(R.layout.msg_right_photo_item, null);
                    } else {
                        convertView = mLayoutInflater.inflate(R.layout.msg_left_photo_item, null);
                    }
                    chatItemView = ChatItemViewFactory.createPhotoItemView(mContext);
                    chatHolder = new PhotoViewHolder(convertView);
                    break;
                case MSG_LEFT_LOCATION:
                case MSG_RIGHT_LOCATION:
                    if (fromMe) {
                        convertView = mLayoutInflater.inflate(R.layout.msg_right_map_item, null);
                    } else {
                        convertView = mLayoutInflater.inflate(R.layout.msg_left_map_item, null);
                    }
                    chatItemView = ChatItemViewFactory.createLocationItemView(mContext);
                    chatHolder = new LocationViewHolder(convertView);
                    break;
            }

            if (convertView != null) {
                if (fromMe) {
                    holderAndItemView = new HolderAndItemView(chatItemView, chatHolder, true);
                } else {
                    holderAndItemView = new HolderAndItemView(chatItemView, chatHolder, false);
                }
                convertView.setTag(holderAndItemView);
            }
        }

        chatItemView.updateItemView(msgList.get(position), chatHolder, position);
        return convertView;
    }
}
