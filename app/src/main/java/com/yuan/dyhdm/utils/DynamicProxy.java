package com.yuan.dyhdm.utils;


import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * created by liangxuedong on 2022/10/28
 */
public class DynamicProxy implements InvocationHandler {

    //目标对象，也就是被代理对象，就是上文的Buyer1 和Buyer12
    private Object realObject;

    public Object newProxyInstance(Object realObject) {
        this.realObject = realObject;
        //返回的是代理对象，这里就是Subject这个接口
        return Proxy.newProxyInstance(realObject.getClass().getClassLoader(),
                realObject.getClass().getInterfaces(), this);

        // Proxy.newProxyInstance（）作用：根据指定的类装载器、一组接口 & 调用处理器 生成动态代理类实例，并最终返回
        // 参数说明：
        // 参数1：指定产生代理对象的类加载器，需要将其指定为和目标对象同一个类加载器
        // 参数2：指定目标对象的实现接口
        // 即要给目标对象提供一组什么接口。若提供了一组接口给它，那么该代理对象就默认实现了该接口，这样就能调用这组接口中的方法
        // 参数3：指定InvocationHandler对象。即动态代理对象在调用方法时，会关联到哪个InvocationHandler对象

    }

    //可以在这里增加一些自己需要的逻辑
    // 参数说明：
    // 参数1：也就是当前的代理对象Object(不是被代理对象)
    // 参数2：当前执行的方法
    // 参数3：当前执行方法的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        UtilsLog.print("invoke(). method:" + method);
        String name = method.getName();
        UtilsLog.print("invoke: name=" + name);
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                UtilsLog.print("invoke: arg=" + arg);
            }
        }
        UtilsLog.print("代购出门了");
        Object result;
        // 通过Java反射机制调用目标对象方法,也就是通过这里调用到了目标对象
            /*举例，相当于执行了被代理对象的方法
            Buyer1 mBuyer1 = new Buyer1();
            mBuyer1.buybuybuy();
            */
        result = method.invoke(realObject, args);
        return result;
    }
}


