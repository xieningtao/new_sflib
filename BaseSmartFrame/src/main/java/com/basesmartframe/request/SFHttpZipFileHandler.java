//package com.basesmartframe.request;
//
//import com.sf.httpclient.newcore.SFHttpFileCallback;
//import com.sf.httpclient.newcore.SFRequest;
//import com.sf.utils.baseutil.ZipUtils;
//
//import java.io.File;
//
///**
// * Created by NetEase on 2016/9/9 0009.
// */
//public class SFHttpZipFileHandler extends SFHttpFileHandler {
//
//    public SFHttpZipFileHandler(SFRequest request, String target, SFHttpFileCallback fileCallback) {
//        super(request, target, fileCallback);
//    }
//
//    @Override
//    public void taskDone(File file) {
//        File desFile = null;
//        if (file != null) {
//            desFile = ZipUtils.decompress(file.getAbsolutePath(), file.getParentFile().getName());
//        }
//        super.taskDone(desFile);
//    }
//}
