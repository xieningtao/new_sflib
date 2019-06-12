package com.sf.SFSample.nybao.bean;

import java.util.List;

/**
 * Created by mac on 16/10/16.
 */

public class NYPicBean {

    private NYPicCoverBean picCoverBean;
    private List<NYPicListBean> picListBean;

    public NYPicCoverBean getPicCoverBean() {
        return picCoverBean;
    }

    public void setPicCoverBean(NYPicCoverBean picCoverBean) {
        this.picCoverBean = picCoverBean;
    }

    public List<NYPicListBean> getPicListBean() {
        return picListBean;
    }

    public void setPicListBean(List<NYPicListBean> picListBean) {
        this.picListBean = picListBean;
    }
}
