package com.yuan.dyhdm.utils;

import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * created by liangxuedong on 2022/10/26
 * 反射工具类
 *反射，指的是对于任意一个类，都可以动态的获得它的所有属性和方法，对于任意一个对象都能调用的它的所有属性和方法，
 * 这种动态获取信息以及动态调用对象方法的功能称为java语言的反射机制。
 */
public class ReflexionUtils {



    public static void printConstructor(String className) {
        try {
            Class<?> aClass = Class.forName(className);
            Constructor<?>[] constructors = aClass.getConstructors();
            UtilsLog.print(constructors);
            Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
            UtilsLog.print(declaredConstructors);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //获得指定的构造方法
    public static Constructor getConstructor(String className, Class<?>... clzs) {
        try {
            Class<?> aClass = Class.forName(className);
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(clzs);
            UtilsLog.print(declaredConstructor);
            //   if Constructor is not public,you should call this
            declaredConstructor.setAccessible(true);
            return declaredConstructor;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;

    }

    //获得所有的 Field 变量
    public static void printField(String className) {
        try {
            Class<?> aClass = Class.forName(className);
            Field[] fields = aClass.getFields();
            UtilsLog.print(fields);
            Field[] declaredFields = aClass.getDeclaredFields();
            UtilsLog.print(declaredFields);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //获得指定的成员变量
    public static Field getFiled(String className, String filedName) {
        Object o = null;
        try {
            Class<?> aClass = Class.forName(className);

            Field declaredField = aClass.getDeclaredField(filedName);
            //   if not public,you should call this
            declaredField.setAccessible(true);
            return declaredField;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;

    }

    //获取所有的 Method
    public static void printMethods(String className) {
        try {
            Class<?> aClass = Class.forName(className);
            Method[] declaredMethods = aClass.getDeclaredMethods();
            UtilsLog.print(declaredMethods);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //获取指定的方法
    public static Method getMethod(String className, String methodName, Class<?>... clzs) {
        try {
            Class<?> aClass = Class.forName(className);
            Method declaredMethod = aClass.getDeclaredMethod(methodName, clzs);
            declaredMethod.setAccessible(true);
        //获取访问权限 public  private
            int modifiers = declaredMethod.getModifiers();
            Modifier.toString(modifiers);

            return declaredMethod;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 利用反射操作数组
     * 1 利用反射修改数组中的元素
     * 2 利用反射获取数组中的每个元素
     */
    public static void testArrayClass() {
        String[] strArray = new String[]{"5","7","暑期","美女","女生","女神"};
        Array.set(strArray,0,"帅哥");
        Class clazz = strArray.getClass();
        if (clazz.isArray()) {
            int length = Array.getLength(strArray);
            for (int i = 0; i < length; i++) {
                Object object = Array.get(strArray, i);
                String className=object.getClass().getName();
                System.out.println("----> object=" + object+",className="+className);
            }
        }
    }

    /**
     * 类加载：编译加载、连接、初始化
     * 1、编译加载
     * （1）通过类的全限定名来获取定义此类的二进制字节流
     * （2）将这个类字节流代表的静态存储结构转为方法区的运行时数据结构
     * （3）在堆中生成一个代表此类的java.lang.Class对象，作为访问方法区这些数据结构的入口
     * 主要是.class字节码文被类加载器也就是ClassLoader加载到内存中并且创建一个class对象
     *
     * 2、
     * （1）校验：其中一项看字节码的数据是否以“魔数cafe”以及当前的JVM的运行JDK版本
     * 是否可以运行。向下兼容，反过来不行。---验证你的代码是否有问题，是否有安全方面的问题
     * （2）准备：给成员变量（类变量/静态变量给默认值），把常量（final）等值在方法区的
     * 常量池准备好---正式为类变量分配内存并设置类变量初始值的阶段，这些内存在方法区分配
     * （3）解析：理解为把类中的对应类型名换成该类型的class对象的地址
     *  String --》String类型对应的Class地址
     * 3、初始化阶段是执行类构造器 < clinit >（） 方法的过程
     *（1）静态变量的显式初始化代码，赋值代码
     * （2）静态代码块
     * 初始化一个类的时候会首先去检查父类是否初始化，如果没有会首先初始化父类
     * jvm会保证一个类的clinit方法在多线程环境下被争取的加锁和同步
     *
     *反射优点：
     * 能够运行时动态获取类的实例，提高灵活性
     * 与动态编译结合
     *
     * 性能问题
     * java反射的性能并不好，原因主要是编译器没法对反射相关的代码做优化
     *
     *
     *
     *
     */


}
