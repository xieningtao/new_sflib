package com.example.androidtv.module.home;

import com.sf.loglib.L;
import com.example.androidtv.TVBaseViewPageActivity;
import com.example.androidtv.module.BaseModule;
import com.sf.httpclient.core.AjaxParams;
import com.sflib.reflection.core.SFIntegerMessage;
import com.sflib.reflection.core.SFMsgId;
import com.sflib.reflection.core.ThreadId;

/**
 * Created by xieningtao on 15-9-15.
 */
@SFIntegerMessage(theadId = ThreadId.BackThread)
public class TVGameModule extends BaseModule {

    public static final String FIRST_PAGE = "1";
    private final String TAG = getClass().getName();

    public static final String PAGE = "page";
    public static final String PAGESIZE = "pageSize";

    /**
     * @param request can't parse null
     */
    @SFIntegerMessage(messageId = SFMsgId.TVMessage.CATEGORY_REQUEST_ID)
    public void onCategoryRequest(TVGameInterface.CategoryRequest request) {
        if (request == null) throw new NullPointerException("request is null");
        L.info(TAG, "method->getCategory,request: " + request);
        AjaxParams params = new AjaxParams();
        params.put(PAGE, request.mPage + "");
        params.put(PAGESIZE, request.mPageSize + "");
        TVGameManager.getInstance().getCategory(params, request.getTaskItem());
    }

    /**
     * @param request can't parse null
     */
    @SFIntegerMessage(messageId = SFMsgId.TVMessage.DETAIL_REQUEST_ID)
    public void onDetailRequest(TVGameInterface.DetailRequest request) {
        if (request == null) throw new NullPointerException("request is null");
        L.info(TAG, "method->getDetailList,request: " + request);
        AjaxParams params = new AjaxParams();
        params.put(PAGE, request.mPage + "");
        params.put(PAGESIZE, request.mPageSize + "");
        params.put(TVBaseViewPageActivity.GAME_ID, request.gameId + "");
        TVGameManager.getInstance().getDetailList(params, request.getTaskItem());
    }

}
