package com.example.sfchat;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.MediaStore;

import com.sf.utils.baseutil.FDToastUtil;

/**
 * Created by NetEase on 2016/8/11 0011.
 */
public class SFAlbumCommand implements SFBaseChatCommand {
    private Fragment mFragment;

    public SFAlbumCommand(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void executeCommand() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        try {
            mFragment.startActivityForResult(intent, SFBaseChatCommand.ALBUM_REQUEST);
        } catch (ActivityNotFoundException e) {
            FDToastUtil.show(mFragment.getActivity(), R.string.no_gallery_installed);
        }
    }
}
