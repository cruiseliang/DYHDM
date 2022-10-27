package com.yuan.dyhdm.utils;

/**
 * created by liangxuedong on 2022/10/27
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class AnnotationUtils {
    /**
     * Annotation（注解）就是Java提供了一种元程序中的元素关联任何信息和着任何元数据（metadata）的途径和方法。
     * Annotion(注解)是一个接口，程序可以通过反射来获取指定程序元素的Annotion对象，然后通过Annotion对象来获取注解里面的元数据。
     *
     *
     * method.getAnnotation(AnnotationName.class);
     * method.getAnnotations();
     * method.isAnnotationPresent(AnnotationName.class);
     *
     */

        static String className="com.yuan.dyhdm.utils.UserAnnotation";
        /**
         * 简单打印出UserAnnotation 类中所使用到的类注解 该方法只打印了 Type 类型的注解
         *
         * @throws ClassNotFoundException
         */
        public static void parseTypeAnnotation() throws ClassNotFoundException {
            Class clazz = Class.forName(className);

            Annotation[] annotations = clazz.getAnnotations();
            for (Annotation annotation : annotations) {
                MethodInfo testA = (MethodInfo) annotation;
                System.out.println("id= \"" + testA.id() + "\"; name= \""
                        + testA.name() + "\"; gid = " + testA.gid());
            }
        }

        /**
         * 简单打印出UserAnnotation 类中所使用到的方法注解 该方法只打印了 Method 类型的注解
         *
         * @throws ClassNotFoundException
         */
        public static void parseMethodAnnotation() {
            Method[] methods = UserAnnotation.class.getDeclaredMethods();
            for (Method method : methods) {
                /*
                 * 判断方法中是否有指定注解类型的注解
                 */
                boolean hasAnnotation = method.isAnnotationPresent(MethodInfo.class);
                if (hasAnnotation) {
                    /*
                     * 根据注解类型返回方法的指定类型注解
                     */
                    MethodInfo annotation = method.getAnnotation(MethodInfo.class);
                    UtilsLog.print("method = " + method.getName() + " ; id = "
                            + annotation.id() + " ; description = "
                            + annotation.name() + "; gid= " + annotation.gid());
                }
            }
        }

        /**
         * 简单打印出UserAnnotation 类中所使用到的方法注解 该方法只打印了 Method 类型的注解
         *
         * @throws ClassNotFoundException
         */
        public static void parseConstructAnnotation() {
            Constructor[] constructors = UserAnnotation.class.getConstructors();
            for (Constructor constructor : constructors) {
                /*
                 * 判断构造方法中是否有指定注解类型的注解
                 */
                boolean hasAnnotation = constructor
                        .isAnnotationPresent(MethodInfo.class);
                if (hasAnnotation) {
                    /*
                     * 根据注解类型返回方法的指定类型注解
                     */
                    MethodInfo annotation = (MethodInfo) constructor
                            .getAnnotation(MethodInfo.class);
                    UtilsLog.print("constructor = " + constructor.getName()
                            + " ; id = " + annotation.id() + " ; description = "
                            + annotation.name() + "; gid= " + annotation.gid());
                }
            }
        }

}
