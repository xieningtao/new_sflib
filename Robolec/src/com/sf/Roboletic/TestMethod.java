package com.sf.Roboletic;

/**
 * Created by NetEase on 2016/7/22 0022.
 */
public class TestMethod {

    private void a(){
        System.out.println("a method");
    }

    private void b(){
        System.out.println("b method");
    }

    public void c(boolean condition){
        if(condition){
            a();
        }else{
            b();
        }
    }
}
