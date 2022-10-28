package com.yuan.dyhdm.entity;

import androidx.annotation.NonNull;

import com.yuan.dyhdm.interfaceP.ISubjectKT;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * created by liangxuedong on 2022/10/28
 */
// 继承：Java 动态代理机制的主类：java.lang.reflect.Proxy
// 实现：与目标对象一样的接口（即上文例子的Subject接口）
public class ProxySubject extends Proxy implements ISubjectKT {

    // 构造函数
    public ProxySubject(InvocationHandler invocationhandler) {
        super(invocationhandler);
    }

    // buybuybuy（）是目标对象实现接口（Subject）中的方法
    // 即$Proxy0类必须实现
    // 所以在使用动态代理类对象时，才可以调用目标对象的同名方法（即上文的buybuybuy（））
    @Override
    public void buybuybuy(@NonNull String mac) {
        try {
            super.h.invoke(this, null, null);
            // 该方法的逻辑实际上是调用了父类Proxy类的h参数的invoke（）
            // h参数即在Proxy.newProxyInstance(ClassLoader loader, Class<?>[]interfaces,InvocationHandler h)传入的第3个参数InvocationHandler对象
            // 即调用了调用处理器的InvocationHandler.invoke（）
            // 而复写的invoke（）利用反射机制：Object result=method.invoke(proxied,args)
            // 从而调用目标对象的的方法 ->>关注4
            return;
        } catch (Error e) {
        } catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
    }

    @Override
    public void sale(@NonNull String iphone) {

    }
}
