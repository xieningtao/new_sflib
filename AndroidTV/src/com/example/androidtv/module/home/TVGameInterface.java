package com.example.androidtv.module.home;

import com.sfhttpclient.core.AjaxParams;

/**
 * Created by xieningtao on 15-9-15.
 */
public class TVGameInterface {
    public enum HttpTaskState {
        IDLE, START, END
    }

    public static class TaskItem {
        private String mTaskName;

        public TaskItem(String taskName) {
            this.mTaskName = taskName;
        }

        public String getTaskName() {
            return mTaskName;
        }
    }

    public static interface TVCategoryHttpRequest {
        void getCategory(AjaxParams requestParams, TaskItem taskItem);

        void getDetailList(AjaxParams requestParams, TaskItem taskItem);
    }

    //------category  start
    public static class CategoryRequest {
        public final int mPage;
        public final int mPageSize;
        private TVGameInterface.TaskItem mTaskItem;

        public CategoryRequest(int page, int pageSize) {
            this.mPage = page;
            this.mPageSize = pageSize;
        }

        public TaskItem getTaskItem() {
            return mTaskItem;
        }

        public void setTaskItem(TaskItem taskItem) {
            mTaskItem = taskItem;
        }
    }


    public static class CategoryResponse {
        public final AjaxParams mParams;

        public CategoryResponse(AjaxParams params) {
            mParams = params;
        }

        @Override
        public String toString() {
            return "CategoryResponse{" +
                    "mParams=" + mParams +
                    '}';
        }
    }

    public static class CategoryCountEvent {
        public final int mCount;

        public CategoryCountEvent(int count) {
            mCount = count;
        }

        @Override
        public String toString() {
            return "CategoryCountEvent{" +
                    "mCount=" + mCount +
                    '}';
        }
    }


    public static CategoryResponse createCategoryResponse(AjaxParams params) {
        return new CategoryResponse(params);
    }

    public static CategoryRequest createCategoryRequest(int page, int pageSize) {
        return new CategoryRequest(page, pageSize);
    }

    public static CategoryCountEvent createCategoryCountEvent(int count) {
        return new CategoryCountEvent(count);
    }
    //------category  end

    //-------detail list start

    public static class DetailRequest {
        public final int mPage;
        public final int mPageSize;
        public final long gameId;
        private TVGameInterface.TaskItem mTaskItem;

        public DetailRequest(int page, int pageSize, long gameId) {
            mPage = page;
            mPageSize = pageSize;
            this.gameId = gameId;
        }


        public TaskItem getTaskItem() {
            return mTaskItem;
        }

        public void setTaskItem(TaskItem taskItem) {
            mTaskItem = taskItem;
        }

        @Override
        public String toString() {
            return "DetailRequest{" +
                    "mPage=" + mPage +
                    ", mPageSize=" + mPageSize +
                    ", gameId=" + gameId +
                    '}';
        }
    }

    public static class DetailResponse {
        public final AjaxParams mParams;

        public DetailResponse(AjaxParams params) {
            mParams = params;
        }
    }

    public static class DetailCountEvent {
        public final long mCount;

        public DetailCountEvent(long count) {
            mCount = count;
        }

        @Override
        public String toString() {
            return "DetailCountEvent{" +
                    "mCount=" + mCount +
                    '}';
        }
    }

    public static DetailResponse createDetailResponse(AjaxParams params) {
        return new DetailResponse(params);
    }

    public static DetailRequest createDetailRequest(int page, int pageSize, long gameId) {
        return new DetailRequest(page, pageSize, gameId);
    }

    public static DetailCountEvent createDetailCountEvent(long count) {
        return new DetailCountEvent(count);
    }
    //------detail list end
}
