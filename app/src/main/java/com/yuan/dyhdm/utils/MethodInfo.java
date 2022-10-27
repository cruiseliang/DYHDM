package com.yuan.dyhdm.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
  created by liangxuedong on 2022/10/27
 * 定义注解 MethodInfo
 * 为方便测试：注解目标为类 方法，属性及构造方法
 * 注解中含有三个元素 id ,name和 gid;
 * id 元素 有默认值 0
 */

@Documented
@Target({ElementType.TYPE,ElementType.METHOD,
        ElementType.FIELD,ElementType.CONSTRUCTOR})
// 表示在运行时解析
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public  @interface MethodInfo {

    /**
     * 注解配置参数名为注解类的方法名
     * a. 所有方法没有方法体，没有参数没有修饰符，实际只允许 public & abstract 修饰符，默认为 public，不允许抛异常
     *
     * b. 方法返回值只能是基本类型，String, Class, annotation, enumeration 或者是他们的一维数组
     *
     * c. 若只有一个默认属性，可直接用 value() 函数。一个属性都没有表示该 Annotation 为 Mark Annotation
     * 可以加 default 表示默认值
     */

    String name() default "xujunTest";
    int id() default 0;
    Class<Long> gid();



}
