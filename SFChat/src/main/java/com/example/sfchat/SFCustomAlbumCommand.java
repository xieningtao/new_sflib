package com.example.sfchat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.basesmartframe.pickphoto.ActivityFragmentContainer;
import com.basesmartframe.pickphoto.PickPhotosFragment;
import com.basesmartframe.pickphoto.PickPhotosPreviewFragment;

/**
 * Created by NetEase on 2016/8/31 0031.
 */
public class SFCustomAlbumCommand implements SFBaseChatCommand {
    public static final int CUSTOM_ALBUM_COMMAND_RESULT = 121;
    private Fragment mFragment;

    public SFCustomAlbumCommand(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void executeCommand() {
        Bundle bundle = new Bundle();
        bundle.putInt(PickPhotosFragment.MAX_IMAGE_NUM, 4);
        Intent intent = new Intent(mFragment.getActivity(), ActivityFragmentContainer.class);
        intent.putExtra(ActivityFragmentContainer.BUNDLE_CONTAINER, bundle);
        intent.putExtra(ActivityFragmentContainer.FRAGMENT_CLASS_NAME, PickPhotosFragment.class.getName());
        mFragment.startActivityForResult(intent, CUSTOM_ALBUM_COMMAND_RESULT);
    }
}
