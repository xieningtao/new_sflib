package com.basesmartframe.share.sharecore;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.sf.loglib.L;
import com.basesmartframe.share.shareitem.XBaseShareItem;


/**
 * Created by xieningtao on 15-8-5.
 */
public class XCopyShareAction implements XShareAction {

    private final String mCopyContent;
    private final Context mContext;

    /**
     * @param copyContent throw nullPointerException if copyContent is null
     * @param context
     */
    public XCopyShareAction(String copyContent, Context context) {
        if (null == copyContent) {
            throw new NullPointerException("copyContent is null");
        }
        mCopyContent = copyContent;
        mContext = context;
    }

    @Override
    public void doShareAction(XBaseShareItem shareItem,OnXShareListener listener) {
        if (null == shareItem) {
            L.error(this, "share item is null");
            return;
        }
        shareToClipBoard();
        if(listener!=null){
            listener.onResult(shareItem.getShareType());
        }
    }

    /**
     * copy content to clipBoard
     */
    private void shareToClipBoard() {
        ClipboardManager clipboard = (ClipboardManager)
                mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", mCopyContent);
        clipboard.setPrimaryClip(clip);
//        ToastUtil.showToast(R.string.share_copy_succeed);
    }
}
