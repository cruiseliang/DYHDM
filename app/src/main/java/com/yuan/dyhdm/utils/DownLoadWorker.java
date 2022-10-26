package com.yuan.dyhdm.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * created by liangxuedong on 2022/10/11
 */
public class DownLoadWorker implements Runnable {
    private String url;
    private CountDownLatch countDownLatch;

    public DownLoadWorker(String url, CountDownLatch countDownLatch) {
        this.url = url;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        //省略无数业务代码
        Log.e("download","线程" + Thread.currentThread().getName() + "开始下载完成");
        countDownLatch.countDown();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        //使用Stream 生成5个线程
        List<Thread> workers = Stream
                .generate(() -> new Thread(new DownLoadWorker("https://image.baidu.com/", countDownLatch)))
                .limit(5)
                .collect(toList());
        //运行线程
        workers.forEach(Thread::start);
        //等待线程完成
        countDownLatch.await();
        Log.e("download","图片已下载完~~~");

    }

}
