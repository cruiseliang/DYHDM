package com.yuan.dyhdm.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.yuan.dyhdm.R;
import com.yuan.dyhdm.entity.UserProxy1;
import com.yuan.dyhdm.interfaceP.ISubjectKT;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * created by liangxuedong on 2022/10/27
 */
public class HookUtils {

private static  final  String TAG=HookUtils.class.getName();

    public static void hookOnClickListener(View view) throws Exception {
        // 第一步：反射得到 ListenerInfo 对象
        Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
        getListenerInfo.setAccessible(true);
        Object listenerInfo = getListenerInfo.invoke(view);
        // 第二步：得到原始的 OnClickListener事件方法
        Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
        Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
        mOnClickListener.setAccessible(true);
        View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);
        // 第三步：用 Hook代理类 替换原始的 OnClickListener
        View.OnClickListener hookedOnClickListener = new HookedClickListenerProxy(originOnClickListener);
        mOnClickListener.set(listenerInfo, hookedOnClickListener);
    }



    public static void hookNotificationManager(final Context context) throws Exception {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Method getService = NotificationManager.class.getDeclaredMethod("getService");
        getService.setAccessible(true);
        // 第一步：得到系统的 sService
        final Object sOriginService = getService.invoke(notificationManager);

        Class iNotiMngClz = Class.forName("android.app.INotificationManager");
        // 第二步：得到我们的动态代理对象
        Object proxyNotiMng = Proxy.newProxyInstance(context.getClass().getClassLoader(), new
                Class[]{iNotiMngClz}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d(TAG, "invoke(). method:" + method);
                String name = method.getName();
                Log.d(TAG, "invoke: name=" + name);
                if (args != null && args.length > 0) {
                    for (Object arg : args) {
                        Log.d(TAG, "invoke: arg=" + arg);
                    }
                }
                Toast.makeText(context.getApplicationContext(), "检测到有人发通知了", Toast.LENGTH_SHORT).show();
                // 操作交由 sOriginService 处理，不拦截通知
                return method.invoke(sOriginService, args);
                // 拦截通知，什么也不做
                //                    return null;
                // 或者是根据通知的 Tag 和 ID 进行筛选
            }
        });
        // 第三步：偷梁换柱，使用 proxyNotiMng 替换系统的 sService
        Field sServiceField = NotificationManager.class.getDeclaredField("sService");
        sServiceField.setAccessible(true);
        sServiceField.set(notificationManager, proxyNotiMng);

    }

    public static void testNotifiy(Context mContext){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, mContext.getPackageName());
        builder.setContentTitle("看房团通知")
                .setContentText("明天房天下免费看房团就要成团啦，时间地点已以短信的方式发送至您手机，请您注意天气安全出行，房天下小编恭迎您的到来，明天见！")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setChannelId("immm")
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context mContext) {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("immm", "消息", 3);
        notificationManager.createNotificationChannel(channel);
    }


    public static void testProxy(){
        //ProxySubject  模拟实现类
        // 1. 创建调用处理器类对象
        DynamicProxy DynamicProxy = new DynamicProxy();

        // 2. 创建目标对象对象
        UserProxy1 mBuyer1 = new UserProxy1();

        // 3. 创建动态代理类 & 对象：通过调用处理器类对象newProxyInstance（）
        // 传入上述目标对象对象
        ISubjectKT Buyer1_DynamicProxy = (ISubjectKT) DynamicProxy.newProxyInstance(mBuyer1);

        // 4. 通过调用动态代理对象方法从而调用目标对象方法
        // 实际上是调用了invoke（），再通过invoke（）里的反射机制调用目标对象的方法
        Buyer1_DynamicProxy.buybuybuy("mac电脑");
        Buyer1_DynamicProxy.sale("苹果手机");

    }


}
