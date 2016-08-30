package com.example.mocklib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by NetEase on 2016/8/22 0022.
 */
public class CgLibTest implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz){
        // 设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        // 通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    // 拦截父类所有方法的调用
    @Override
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        System.out.println("begin...");
        // 通过代理类调用父类中的方法
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("end...");
        return null;
    }

    public static void main(String[] args){
        CgLibTest proxy = new CgLibTest();
        Animal dog = (Animal)proxy.getProxy(Dog.class);
        dog.sound();
    }
}
