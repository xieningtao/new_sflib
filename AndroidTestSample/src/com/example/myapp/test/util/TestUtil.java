package com.example.myapp.test.util;

/**
 * Created by xieningtao on 16-2-27.
 */
public class TestUtil {

    public static void wait(int millisions){
        try {
            Thread.sleep(millisions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
