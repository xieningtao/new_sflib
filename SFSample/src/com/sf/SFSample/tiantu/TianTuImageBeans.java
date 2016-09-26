package com.sf.SFSample.tiantu;

import java.util.List;

/**
 * Created by NetEase on 2016/9/18 0018.
 */
public class TianTuImageBeans {

    private List<TianTuImageBean> mTianTuImageBeanList;

    public List<TianTuImageBean> getTianTuImageBeanList() {
        return mTianTuImageBeanList;
    }

    public void setTianTuImageBeanList(List<TianTuImageBean> tianTuImageBeanList) {
        mTianTuImageBeanList = tianTuImageBeanList;
    }

    public static class TianTuImageBean {
        private String originUrl;

        public String getOriginUrl() {
            return originUrl;
        }

        public void setOriginUrl(String originUrl) {
            this.originUrl = originUrl;
        }
    }
}
