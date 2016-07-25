package com.sf.Roboletic;

/**
 * Created by NetEase on 2016/7/18 0018.
 */
public class LoginTask {

    public static class TestBean {
        private String mName;

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        @Override
        public String toString() {
            return "TestBean{" +
                    "mName='" + mName + '\'' +
                    '}';
        }
    }
    public  interface LoginImpl{
        int login(String userName,String passWord);
    }

    public interface UpdateLoginView{
        void updateView(TestBean testBean);
    }
}
