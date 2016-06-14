package com.basesmartframe.filecache.cache;

import com.basesmartframe.basepull.PullUI;
import com.sf.httpclient.core.AjaxParams;

/**
 * Created by xieningtao on 15-5-21.
 */
public class ResponsePullListEntity {

    public final PullUI.RefreshType mRefreshType;
    public final boolean resultState;
    public final boolean isIncrement;
    public final AjaxParams mRequestParams;

    private ResponsePullListEntity(ResponsePullListEntityBuilder builder){
        this.mRefreshType=builder.refreshType;
        this.isIncrement=builder.isIncrement;
        this.resultState=builder.resultState;
        this.mRequestParams=builder.params;
    }
    public static class ResponsePullListEntityBuilder{
        private final PullUI.RefreshType refreshType;
        private final boolean resultState;
        private boolean isIncrement;
        private AjaxParams params;

        public ResponsePullListEntityBuilder(PullUI.RefreshType refreshType,boolean resultState) {
            this.refreshType = refreshType;
            this.resultState=resultState;
        }

        public ResponsePullListEntityBuilder setIncrement(boolean isIncrement) {
            this.isIncrement = isIncrement;
            return this;
        }

        public ResponsePullListEntityBuilder setParams(AjaxParams params) {
            this.params = params;
            return this;
        }

        public ResponsePullListEntity builder(){
            return new ResponsePullListEntity(this);
        }
    }
}
