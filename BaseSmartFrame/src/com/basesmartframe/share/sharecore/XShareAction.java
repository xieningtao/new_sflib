package com.basesmartframe.share.sharecore;


import com.basesmartframe.share.shareitem.XBaseShareItem;

/**
 * Created by xieningtao on 15-8-5.
 */
public interface XShareAction {

    void doShareAction(XBaseShareItem shareItem,OnXShareListener onXShareListener);
}
