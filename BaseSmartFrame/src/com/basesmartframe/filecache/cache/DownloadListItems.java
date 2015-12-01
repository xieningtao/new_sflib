package com.basesmartframe.filecache.cache;

import com.basesmartframe.baseutil.SFBus;
import com.basesmartframe.filecache.BaseFileCacheMessage;
import com.basesmartframe.log.L;
import com.google.gson.Gson;
import com.sfhttpclient.core.AjaxParams;

/**
 * Created by xieningtao on 15-5-21.
 */
public class DownloadListItems extends DownloadTask {


    protected final RequestPullListEntity mEntity;

    /**
     * @param url
     * @param entity
     * @throws Exception if entity is null
     */
    public DownloadListItems(String url, RequestPullListEntity entity) throws Exception {
        super(url);
        if (null == entity) {
            throw new IllegalArgumentException("entity is null");
        }
        this.mEntity = entity;
    }

    public void excuteGet() {
        realRunGet(mEntity.params);
    }

    public void excutePost() {
        realRunPost(mEntity.params);
    }

    @Override
    public void onResponse(boolean success, AjaxParams params, String response) {
        Object result = null;
        boolean isIncrement = false;
        if (success) {
            try {
                Gson gson = new Gson();
                result = gson.fromJson(response, mEntity.mResult);
                if (null != result) {
                    isIncrement = isIncrement(response);
                } else {
                    L.error(this, "result.data is null");
                }

            } catch (Exception e) {
                L.error(TAG, "parse data fail : " + e);
            }
        }

        ResponsePullListEntity responsePullListEntity = new ResponsePullListEntity.
                ResponsePullListEntityBuilder(mEntity.type, success)
                .setIncrement(isIncrement)
                .setParams(params)
                .builder();

        sendResult(result, responsePullListEntity);
    }

    private void sendResult(Object result, ResponsePullListEntity responsePullListEntity) {
        Class message_class = mEntity.mMessage;
        if (message_class != null) {
            try {
                BaseFileCacheMessage message_instance = (BaseFileCacheMessage) message_class.newInstance();
                message_instance.setResponsePullListEntity(responsePullListEntity);
                message_instance.setResult(result);
                SFBus.post(message_instance);
            } catch (InstantiationException e) {
                e.printStackTrace();
                //TODO
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                //TODO
            }

        } else {
            //TODO
        }
    }

    private Boolean isRelIncrement(String result) {
        RequestPullListEntity.PaggingEvent paggingEvent = mEntity.mPaggingEvent;
        if (null == paggingEvent) return false;
        final int curOffset = paggingEvent.getOffSet(mEntity.params);
        final int pageSize = paggingEvent.getPageSize(mEntity.params);
        final int total = paggingEvent.getTotalPage(result);
        final int curTotalNum = pageSize * curOffset;
        return total > curTotalNum ? Boolean.TRUE : Boolean.FALSE;
    }

    protected boolean isIncrement(String result) {
        boolean isIncrement = false;
        if (null != result) {
            isIncrement = isRelIncrement(result);
        } else {
            L.error(this, "result.data is null");
        }
        return isIncrement;
    }


}
