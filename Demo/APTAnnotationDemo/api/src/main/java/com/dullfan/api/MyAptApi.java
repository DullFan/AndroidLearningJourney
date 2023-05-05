package com.dullfan.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyAptApi {
    @SuppressWarnings("all")
    public static void init(){
        try {
            Class<?> aClass = Class.forName("com.dullfan.aptannotationdemo.HelloWorld");
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
            Object o = declaredConstructor.newInstance();
            Method test = aClass.getDeclaredMethod("test", String.class);
            test.invoke(o,"参数值");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
