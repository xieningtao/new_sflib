package com.example.androidtv.module.bean;

import java.util.List;

/**
 * Created by xieningtao on 15-9-15.
 */
public class TVHomeDataModel {

    //-------------category start

    public static class TVCategoryData {
        private int count;
        private int page;
        private int pageSize;
        private List<TVCategoryBean> itemList;

        public void setCount(int count) {
            this.count = count;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setItemList(List<TVCategoryBean> itemList) {
            this.itemList = itemList;
        }

        public List<TVCategoryBean> getCategoryBeans() {
            return itemList;
        }

        public int getCount() {
            return count;
        }

        public int getPage() {
            return page;
        }

        public int getPageSize() {
            return pageSize;
        }

        @Override
        public String toString() {
            return "TVCategoryData{" +
                    "count=" + count +
                    ", mPage=" + page +
                    ", mPageSize=" + pageSize +
                    ", itemList=" + itemList +
                    '}';
        }
    }

    public static class TVCategoryBean {

        private long itemId;
        private String itemName;
        private String imgUrl;

        public void setItemId(long itemId) {
            this.itemId = itemId;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public long getItemId() {
            return itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        @Override
        public String toString() {
            return "TVCategoryBean{" +
                    "itemId=" + itemId +
                    ", itemName='" + itemName + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    '}';
        }
    }
    //-----------category end


    //------------detail list start

    public static class TVDetailListBean {
        private long lChannelId;
        private long lSubchannel;
        private String sNick;
        private String sSubchannelName;
        private String sAvatarUrl;
        private int iAttendeeCount;
        private int iGameId;
        private String sGameName;
        private String sVideoCaptureUrl;
        private int iRecType;
        private int live_type;
        private int showAvatar;

        public long getlChannelId() {
            return lChannelId;
        }

        public long getlSubchannel() {
            return lSubchannel;
        }

        public String getsNick() {
            return sNick;
        }

        public String getsSubchannelName() {
            return sSubchannelName;
        }

        public String getsAvatarUrl() {
            return sAvatarUrl;
        }

        public int getiAttendeeCount() {
            return iAttendeeCount;
        }

        public int getiGameId() {
            return iGameId;
        }

        public String getsGameName() {
            return sGameName;
        }

        public String getsVideoCaptureUrl() {
            return sVideoCaptureUrl;
        }

        public int getiRecType() {
            return iRecType;
        }

        public int getLive_type() {
            return live_type;
        }

        public int getShowAvatar() {
            return showAvatar;
        }

        @Override
        public String toString() {
            return "TVDetailListBean{" +
                    "lChannelId=" + lChannelId +
                    ", lSubchannel=" + lSubchannel +
                    ", sNick='" + sNick + '\'' +
                    ", sSubchannelName='" + sSubchannelName + '\'' +
                    ", sAvatarUrl='" + sAvatarUrl + '\'' +
                    ", iAttendeeCount=" + iAttendeeCount +
                    ", iGameId=" + iGameId +
                    ", sGameName='" + sGameName + '\'' +
                    ", sVideoCaptureUrl='" + sVideoCaptureUrl + '\'' +
                    ", iRecType=" + iRecType +
                    ", live_type=" + live_type +
                    ", showAvatar=" + showAvatar +
                    '}';
        }
    }


    //------------detail list end


}
