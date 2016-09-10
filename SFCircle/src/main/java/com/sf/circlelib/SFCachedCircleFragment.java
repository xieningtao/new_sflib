package com.sf.circlelib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.pickphoto.ImageBean;
import com.basesmartframe.request.SFHttpGsonHandler;
import com.nostra13.universalimageloader.utils.L;
import com.sf.circlelib.abscircle.ISFComment;
import com.sf.circlelib.abscircle.ISFContent;
import com.sf.circlelib.abscircle.ISFImage;
import com.sf.circlelib.circledb.DBComment;
import com.sf.circlelib.circledb.DBContent;
import com.sf.circlelib.circledb.DBImage;
import com.sf.dblib.DbUtils;
import com.sf.dblib.exception.DbException;
import com.sf.dblib.sqlite.FinderLazyLoader;
import com.sf.dblib.sqlite.Selector;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.SFRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SFCachedCircleFragment extends SFCircleFragment<SFContent, SFImage, SFComment> {

    private DbUtils mDbUtils;

    private void doRequest() {
        String url = "https://www.baidu.com/";
        SFRequest request = new SFRequest(url, MethodType.GET) {
            @Override
            public Class getClassType() {
                return SFContent.class;
            }
        };
        SFHttpGsonHandler handler = new SFHttpGsonHandler(request, new SFHttpStringCallback<SFContent>() {

            @Override
            public void onSuccess(SFRequest request, SFContent g) {
                updateDataFromDb();
            }

            @Override
            public void onFailed(SFRequest request, Exception e) {
                updateDataFromDb();
            }
        });
        handler.start();
    }

    @Override
    protected boolean onRefresh() {
        doRequest();
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDbUtils = DbUtils.create(getActivity());
    }

    @Override
    protected SFImage getNineGrideItem(int childPos, SFContent bean) {
        return (SFImage) bean.getImages().get(childPos);
    }

    @Override
    protected SFComment getCommentListItem(int childPos, SFContent bean) {
        return (SFComment) bean.getComments().get(childPos);
    }

    @Override
    protected boolean onLoadMore() {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateDataFromDb();
    }

    private void updateDataFromDb() {
        try {
            List<DBContent> dbContentList = mDbUtils.findAll(Selector.from(DBContent.class));

            finishRefreshOrLoading(dbContentToSFContent(dbContentList), false);
        } catch (DbException e) {
            L.e(TAG, "onActivityResult exception: " + e);
        }
    }

    private List<SFContent> dbContentToSFContent(List<DBContent> contentList) {
        List<SFContent> sfContentList = new ArrayList<>();
        if (contentList == null || contentList.isEmpty()) {
            return sfContentList;
        }
        try {
            for (DBContent dbContent : contentList) {
                SFContent content = new SFContent();
                content.setContent(dbContent.getContent());
                List<ISFImage> imageList = new ArrayList<>();
                FinderLazyLoader<DBImage> dbImages = dbContent.getImages();
                if (dbImages != null) {
                    List<DBImage> dbImageList=dbImages.getAllFromDb();
                    if(dbImageList!=null&&!dbImageList.isEmpty()) {
                        for (DBImage dbImage : dbImageList) {
                            SFImage sfImage = new SFImage();
                            sfImage.setUrl("file://" + dbImage.getUrl());
                            imageList.add(sfImage);
                        }
                    }
                    content.setImages(imageList);
                }
                List<ISFComment> commentList = new ArrayList<>();
                FinderLazyLoader<DBComment> dbComments = dbContent.getComments();
                if (dbComments != null) {
                    List<DBComment> dbCommentList=dbComments.getAllFromDb();
                    if(dbCommentList!=null&&!dbCommentList.isEmpty()) {
                        for (DBComment dbComment : dbCommentList) {
                            SFComment comment = new SFComment();
                            comment.setComment(dbComment.getComment());
                            comment.setFromId(dbComment.getFromId());
                            comment.setToId(dbComment.getToId());
                            comment.setFromName(dbComment.getFromName());
                            comment.setToName(dbComment.getToName());
                            commentList.add(comment);
                        }
                    }
                    content.setComments(commentList);
                }
                sfContentList.add(content);
            }
            return sfContentList;
        } catch (DbException e) {
            L.e(TAG, "dbContentToSFContent exception: " + e);
        }
        return sfContentList;
    }
}
