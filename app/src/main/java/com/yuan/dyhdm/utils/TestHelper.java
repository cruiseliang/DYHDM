package com.yuan.dyhdm.utils;


import android.util.Log;

import com.yuan.dyhdm.entity.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * created by liangxuedong on 2022/10/26
 */
public class TestHelper {

    public static  final String TAG="xujun";
    public static final String CLASS_NAME = "com.yuan.dyhdm.entity.Person";
    public static final String CHINA = "China";

    public static void testConstructor(){
//        ReflexionUtils.printConstructor(CLASS_NAME);
//        ReflexionUtils.printField(CLASS_NAME);
        Constructor constructor = ReflexionUtils.getConstructor(CLASS_NAME, String.class, Integer.class);
        try {
            Object meinv = constructor.newInstance(CHINA, 12);
            Person person = (Person) meinv;
            UtilsLog.print(person);
            Field field = ReflexionUtils.getFiled(CLASS_NAME, "age");

            Method method = ReflexionUtils.getMethod(CLASS_NAME,
                    "setCountry", String.class);
            try {
                Integer integer = (Integer) field.get(person);
                UtilsLog.print("integer="+integer);

                // 执行方法，结果保存在 person 中
                Object o = method.invoke(person, CHINA);
                // 拿到我们传递进取的参数 country 的值 China
                String country=person.country;
                UtilsLog.print(country);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "testConstructor: =" + person.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
