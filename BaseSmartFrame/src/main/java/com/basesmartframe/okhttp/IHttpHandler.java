package com.basesmartframe.okhttp;


import com.sf.loglib.L;

public interface IHttpHandler {
    void onTokenUnAvailable();

    void checkValidate(int resultCode, int statusCode);

    boolean refreshToken();

    class Default {
        public static IHttpHandler getHandler() {
            IHttpHandler handler = null;
            //Router.getApiService(IHttpHandler.class);
            return handler != null ? handler : new IHttpHandler() {
                @Override
                public void onTokenUnAvailable() {
                    L.error("IHttpHandler", "账号过期请重新登录");
                }

                @Override
                public void checkValidate(int resultCode, int statusCode) {

                }

                @Override
                public boolean refreshToken() {
                    return false;
                }
            };
        }
    }
}
