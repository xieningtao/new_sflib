package com.basesmartframe.filecache.cache;

import com.basesmartframe.basepull.PullUI;
import com.basesmartframe.filecache.BaseFileCacheMessage;
import com.basesmartframe.log.L;
import com.sf.httpclient.core.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by xieningtao on 15-5-21.
 */
public class RequestPullListEntity {
    public static interface PaggingEvent {
        int getPageSize(AjaxParams params);

        int getOffSet(AjaxParams params);

        int getTotalPage(String result);
    }

    public final AjaxParams params;
    public final PullUI.RefreshType type;
    public final Class<? extends BaseFileCacheMessage> mMessage;
    public final Class mResult;
    public final PaggingEvent mPaggingEvent;

    private RequestPullListEntity(RequestEntityBuilder builder) {
        this.params = builder.params;
        this.type = builder.type;
        this.mMessage = builder.message;
        this.mResult = builder.result;
        this.mPaggingEvent = builder.event;
    }

    public static class RequestEntityBuilder {
        private AjaxParams params;
        private PaggingEvent event;
        private final Class<? extends BaseFileCacheMessage> message;
        private final PullUI.RefreshType type;
        private final Class result;

        public RequestEntityBuilder(PullUI.RefreshType type, Class<? extends BaseFileCacheMessage> message, Class result) {
            this.type = type;
            this.message = message;
            this.result = result;
        }

        public RequestEntityBuilder setParams(AjaxParams params) {
            this.params = params;
            return this;
        }

        public RequestEntityBuilder setEvent(PaggingEvent event) {
            this.event = event;
            return this;
        }

        public RequestPullListEntity builder() {
            return new RequestPullListEntity(this);
        }
    }

    public static final PaggingEvent PAGGEING_EVENT = new PaggingEvent() {
        @Override
        public int getPageSize(AjaxParams params) {
            String curOffset = "0";
            Map<String, String> urlParams = params.getUrlParams();
            if (urlParams.containsKey("page")) {
                curOffset = params.getUrlParams().get("page");
            }
            return Integer.valueOf(curOffset);
        }

        public int getOffSet(AjaxParams params) {
            String pageSize = "10";
            Map<String, String> urlParams = params.getUrlParams();
            if (urlParams.containsKey("pageSize")) {
                pageSize = params.getUrlParams().get("pageSize");
            }
            return Integer.valueOf(pageSize);
        }

        public int getTotalPage(String result) {
            int total = 0;
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("data")) {
                    JSONObject dataJson = jsonObject.getJSONObject("data");
                    if (dataJson.has("totalnum")) {
                        total = dataJson.getInt("totalnum");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                L.error(this, "fail to parse json reason: " + e);
            }
            return total;
        }
    };
}
