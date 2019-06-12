//package com.basesmartframe.updateutil;
//
//import android.app.Activity;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnCancelListener;
//import android.content.DialogInterface.OnClickListener;
//import android.content.DialogInterface.OnDismissListener;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.TextView;
//
//import com.netease.huatian.R;
//import com.netease.huatian.constant.ApiUrls;
//import com.netease.huatian.utils.HttpUtils;
//import com.netease.huatian.utils.StringUtils;
//import com.netease.huatian.utils.Utils;
//import com.netease.huatian.view.CustomDialog;
//import com.netease.huatian.view.CustomProgressDialog;
//import com.netease.huatian.view.CustomToast;
//import com.netease.mobsecurity.interfacejni.SecruityInfo;
//import com.netease.util.sp.PrefHelper;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 检查应用自更新的工具类
// *
// * @author zmtian
// */
//
//public class CheckUpdateUtils {
//
//    public static final String DATA = "data";
//
//    public static final String VERSION = "version";
//
//    public static final String UPDATE_INFO = "updateInfo";
//
//    public static final String UPDATE_URL = "url";
//
//    private static final String PLATFORM_KEY_ANDROID = "2";
//
//    private static final String UPDATE_NOTIFY = "updateNotify";
//
//    /**
//     * 目前showProgress也表示isForceCheckUpdate的意思
//     */
//    public static void checkUpdate(Activity activity, boolean showProgress) {
//        boolean updateNotify = PrefHelper.getBoolean(activity, UPDATE_NOTIFY, true);
//        if (updateNotify || showProgress) {
//            UpdateTask updateTask = new UpdateTask(activity, showProgress);
//            updateTask.execute();
//        }
//    }
//
//    public static class UpdateTask extends AsyncTask<Void, Void, JSONObject> implements OnCancelListener {
//
//        private Activity mActivity;
//
//        private CustomProgressDialog mProgressDialog;
//
//        private boolean mShowProgress;
//
//        public UpdateTask(Activity activity, boolean showProgress) {
//            mActivity = activity;
//            mShowProgress = showProgress;
//        }
//
//        @Override
//        protected JSONObject doInBackground(Void... params) {
//
//            SecruityInfo secInfo = new SecruityInfo(mActivity);
//            String deviceInfo = secInfo.getSecInfo();
//
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//            list.add(new BasicNameValuePair("access_token", Utils.getToken(mActivity)));
//            list.add(new BasicNameValuePair("platform", PLATFORM_KEY_ANDROID));
//            list.add(new BasicNameValuePair("deviceInfo", deviceInfo));
//            String updateInfo = HttpUtils.sendHttpPost(mActivity, ApiUrls.UPDATE_URL, list);
//            JSONObject jobj = StringUtils.StringToJson(updateInfo);
//            if (jobj != null) {
//                try {
//                    jobj = jobj.getJSONObject(DATA);
//                    int versionCode = jobj.isNull(VERSION) ? 0 : jobj.getInt(VERSION);
//                    String url = jobj.getString(UPDATE_URL);
//                    if (!TextUtils.isEmpty(url)) {
////                        int installedVersionCode = SystemUtils.getVersionCode(mActivity);
//                        int installedVersionCode = 3700;
//                        if (installedVersionCode < versionCode) {
//                            return jobj;
//                        }
//                    }
//                } catch (JSONException e) {
//
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(final JSONObject jobj) {
//            if (mProgressDialog != null) {
//                mProgressDialog.dismiss();
//            }
//
//            if (jobj != null) {
//                try {
//                    String updateContent = jobj.isNull(UPDATE_INFO) ? mActivity.getString(R.string.update_text_content) : jobj
//                            .getString(UPDATE_INFO);
//                    final String updateUrl = jobj.getString(UPDATE_URL);
//
//                    CustomDialog dialog = new CustomDialog(mActivity);
//                    if (mShowProgress) {
//                        dialog.setMessage(updateContent);
//                    } else {
//                        final View view = View.inflate(mActivity, R.layout.update_layout, null);
//                        TextView updateText = (TextView) view.findViewById(R.id.update_message);
//                        updateText.setText(updateContent);
//
//                        dialog.setView(view);
//                        dialog.setOnDismissListener(new OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//                                CheckBox checkBox = (CheckBox) view.findViewById(R.id.notice_checkbox);
//                                PrefHelper.putBoolean(mActivity, UPDATE_NOTIFY, !checkBox.isChecked());
//                            }
//                        });
//                    }
//
//                    dialog.setDialogTitle(R.string.update_title).setPositiveButton(R.string.confirm, new OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent i = new Intent(mActivity.getApplicationContext(), UpdateService.class);
//                            i.putExtra(UPDATE_URL, updateUrl);
//                            mActivity.startService(i);
//                        }
//                    }).setNegativeButton(R.string.cancel, null);
//                    try {
//                        dialog.show();
//                    } catch (Exception e) {
//
//                    }
//
//                } catch (JSONException e) {}
//
//            } else if (mShowProgress) {
//                CustomToast.showToast(mActivity, R.string.no_update);
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            if (mProgressDialog != null) {
//                mProgressDialog.dismiss();
//            }
//        }
//
//        protected void onPreExecute() {
//            if (mShowProgress) {
//                mProgressDialog = new CustomProgressDialog(mActivity);
//                mProgressDialog.show();
//            }
//        }
//
//        @Override
//        public void onCancel(DialogInterface dialog) {
//            cancel(true);
//        }
//    }
//}
