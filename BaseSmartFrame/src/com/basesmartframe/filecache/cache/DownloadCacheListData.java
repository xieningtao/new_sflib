package com.basesmartframe.filecache.cache;

import android.text.TextUtils;

import com.sf.utils.baseutil.GsonUtil;
import com.basesmartframe.filecache.BaseFileCacheListMessage;
import com.basesmartframe.filecache.BaseFileCacheMessage;
import com.basesmartframe.filecache.cacheentry.FileCacheManager;
import com.sf.loglib.L;
import com.sf.httpclient.core.AjaxParams;


/**
 * Created by xieningtao on 15-5-21.
 */
public class DownloadCacheListData extends DownloadListItems {
    private final CacheEntity mCacheEntity;

    /**
     * @param url
     * @param entity
     * @throws Exception if entity is null
     */
    public DownloadCacheListData(String url, RequestPullListEntity entity, CacheEntity cacheEntity) throws Exception {
        super(url, entity);
        this.mCacheEntity = cacheEntity;
    }

    public void runGet(AjaxParams params, boolean runInRunning) {
        if (mCacheEntity.mType == DataSrcType.LOCAL) {
            onResponse(true, params, "");
        } else {
            super.excuteGet();
        }
    }

    public void runPost(AjaxParams params, boolean runInRunning) {
        if (mCacheEntity.mType == DataSrcType.LOCAL) {
            onResponse(true, params, "");
        } else {
            super.excutePost();
        }
    }

    public void onResponse(boolean success, AjaxParams params, String response) {
        Object sendResult = null;
        Boolean isIncrement = Boolean.FALSE;
        int newCountNum = 0;
        //local
        Object cacheResult = null;
        if (mCacheEntity.mType == DataSrcType.LOCAL || mCacheEntity.mType == DataSrcType.LOCAL_NETWORK) {
            cacheResult = getLocalCacheData();
//            isIncrement=isIncrement(cacheResult);
        }
        //network
        Object networkResult = null;
        if (mCacheEntity.mType == DataSrcType.NETWORK || mCacheEntity.mType == DataSrcType.LOCAL_NETWORK) {
            if (success) {
                networkResult = getNetWorkData(response);
                isIncrement = isIncrement(response);
            }
        }

        if (mCacheEntity.mType == DataSrcType.LOCAL_NETWORK) {
            CacheEntity.DiffNetWorkCache cacheEvent = mCacheEntity.getmCacheEvent();
            if (null != cacheEvent && mCacheEntity.isCurPageCached(params)) {
                newCountNum = cacheEvent.onDiffNetworkAndCache(networkResult, cacheResult);
            }
        }

        //save into local
        if (success && mCacheEntity.mType == DataSrcType.LOCAL_NETWORK && mCacheEntity.isCurPageCached(params)) {
            boolean save = saveDataIntoCache(response);
            L.info(this, "save response: " + save);
        }

        //send data to ui
        ResponsePullListEntity responsePullListEntity = new ResponsePullListEntity.
                ResponsePullListEntityBuilder(mEntity.type, success)
                .setIncrement(isIncrement)
                .setParams(params)
                .builder();

        if (mCacheEntity.mType == DataSrcType.LOCAL) {
            sendResult = cacheResult;
        } else {
            sendResult = networkResult;
        }
        sendResult(sendResult, responsePullListEntity, newCountNum);
    }

    private void sendResult(Object result, ResponsePullListEntity responsePullListEntity, int newCountNum) {
        Class message_class = mEntity.mMessage;
        try {
            BaseFileCacheMessage message_instance = (BaseFileCacheMessage) message_class.newInstance();
            if (message_instance instanceof BaseFileCacheListMessage) {
                BaseFileCacheListMessage listMessage_instance = (BaseFileCacheListMessage) message_instance;
                listMessage_instance.setResponsePullListEntity(responsePullListEntity);
                listMessage_instance.setResult(result);
                listMessage_instance.setNewCount(newCountNum);
                listMessage_instance.setSucces(true);
                //TODO change eventBug to SFBus
//                EventBus.getDefault().post(listMessage_instance);
            } else {
                throw new IllegalAccessException("message_instance is not instanceof BaseFileCacheListMessage");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            L.error(TAG, "exception: " + e.getMessage());
            //TODO
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            //TODO
        }

    }

    private boolean saveDataIntoCache(String response) {
        String key = mCacheEntity.mDirKey;
        String page = mCacheEntity.mPageKey + "";
        return FileCacheManager.getInstance().save(key, page, response);
    }

    private Object getNetWorkData(String response) {
        return GsonUtil.parse(response, mEntity.mResult);
    }


    private Object getLocalCacheData() {
        L.info(this, "cacheKey: " + mCacheEntity.mDirKey + " pageKey: " + mCacheEntity.mPageKey);
        Object result = null;
        if (FileCacheManager.getInstance().isCacheExist(mCacheEntity.mDirKey, mCacheEntity.mPageKey + "")) {
            String cacheResponse = FileCacheManager.getInstance().get(mCacheEntity.mDirKey, mCacheEntity.mPageKey + "");
            if (!TextUtils.isEmpty(cacheResponse)) {
                result = GsonUtil.parse(cacheResponse, mEntity.mResult);
            }
        } else {
            L.error(this, "there is no cache file");
        }

        return result;
    }


}
