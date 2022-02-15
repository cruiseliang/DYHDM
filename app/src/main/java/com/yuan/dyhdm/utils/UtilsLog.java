package com.yuan.dyhdm.utils;

import android.os.Environment;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilsLog {
    /**
     * 是否开启日志
     */
//    public static boolean isTest = false;
    public static boolean isTest = true;

    public static boolean isLog = false;// 是否保存log日志到本地目录/ckgh/log

//    public static boolean deaultConfig = false;
    public static boolean deaultConfig = false;

    public static boolean deault = isTest && deaultConfig;

    public static boolean isHttps = !deault;


    public static void d(String key, String value) {
        if (isTest) {
            android.util.Log.d(key, value);
        }
    }

    public static void i(String key, String value) {
        if (isTest) {
            android.util.Log.i(key, value);
        }
    }

    public static void e(String key, String value) {
        if (isTest&&value!=null) {
            android.util.Log.e(key, value);
        }
    }

    public static void w(String key, String value) {


        if (isTest) {
            android.util.Log.w(key, value);
        }
    }

    public static void w(String key, Throwable tr) {
        if (isTest) {
            android.util.Log.w(key, tr);
        }
    }

    public static void w(String key, String value, Throwable tr) {
        if (isTest) {
            android.util.Log.w(key, value, tr);
        }
    }

    public static void log(String tag, String info) {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        int i = 1;
        if (isTest) {
            StackTraceElement s = ste[i];
            android.util.Log.e(tag, String.format("======[%s][%s][%s]=====%s", s.getClassName(),
                    s.getLineNumber(), s.getMethodName(), info));
        }
    }

    /**
     * @param content
     *            保存到本地的内容
     */
    public static void writeLog(String content) {
        if (isTest && isLog) {
            // log("write", "=====");
            int day, month;
            File localFile;
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault());
            content += "====" + formatter.format(date) + "====";
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            localFile = new File(getTxtFile(), month + "月" + day + "日" + ".txt");
            try (FileWriter fileWriter = new FileWriter(localFile, true);
                 BufferedWriter bWriter = new BufferedWriter(fileWriter);){
                // if (localFile.exists()) {

                bWriter.write(content);
                bWriter.newLine();
                bWriter.flush();
                // }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static File getTxtFile() {
        File voiceFile = new File(Environment.getExternalStorageDirectory().toString() + "/"
                + "ckgh" + "/" + "Log" + "/");

        if (!voiceFile.exists()) {
            voiceFile.mkdirs();
        }
        return voiceFile;
    }
}
