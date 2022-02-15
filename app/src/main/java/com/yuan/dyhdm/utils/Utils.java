package com.yuan.dyhdm.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.location.LocationManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.yuan.dyhdm.MyApplication;
import com.yuan.dyhdm.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Utils {

    public static Handler handler;

    




    /**
     * 获得指定目录的剩余空间.
     *
     * @param path
     * @return
     */
    public static long getFreeSpace(String path) {
        StatFs sf = new StatFs(path);
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();

        return availCount * blockSize;
    }

    /**
     * toast （默认 时间Toast.LENGTH_LONG）
     *
     * @param c
     * @param msg 内容
     */
    public static void toast(Context c, String msg) {
        toast(c, msg, true);
    }

    /**
     * toast
     *
     * @param c
     * @param msg      内容
     * @param duration 时间
     */
    public static void toast(Context c, String msg, int duration) {
        toast(c, msg, true, duration);
    }

    /**
     * toast 时间（默认 时间Toast.LENGTH_LONG）
     *
     * @param c
     * @param msg  内容
     * @param show 是否显示
     */
    public static void toast(Context c, String msg, boolean show) {
//		toast(c, msg, show, Toast.LENGTH_LONG);
        toast(c, msg, show, Toast.LENGTH_SHORT);
    }

    /**
     * toast
     *
     * @param c
     * @param msg      内容
     * @param show     是否显示
     * @param duration 时间
     */
    public static void toast(Context c, String msg, boolean show, int duration) {
        if (!show)
            return;
        MyApplication.toastMgr.builder.display(msg, duration);
    }

    /**
     * toast 时间（默认 时间Toast.LENGTH_LONG）
     *
     * @param c
     * @param resid 内容资源id
     */
    public static void toast(Context c, int resid) {
        toast(c, resid, true);
    }

    /**
     * toast
     *
     * @param c
     * @param resid    内容资源id
     * @param duration 时间
     */
    public static void toast(Context c, int resid, int duration) {
        toast(c, resid, true, duration);
    }

    /**
     * toast 时间（默认 时间Toast.LENGTH_LONG）
     *
     * @param c
     * @param resid 内容资源id
     * @param show  是否显示
     */
    public static void toast(Context c, int resid, boolean show) {
        toast(c, resid, show, Toast.LENGTH_LONG);
    }

    /**
     * toast
     *
     * @param c
     * @param resid    内容资源id
     * @param show     是否显示
     * @param duration 时间
     */
    public static void toast(Context c, int resid, boolean show, int duration) {
        if (!show)
            return;
        MyApplication.toastMgr.builder.display(resid, duration);
    }

    public static void toast(Context c, String msg, boolean show, int duration, int position,
                             int yOffset) {
        if (!show)
            return;
        MyApplication.toastMgr.builder.display(msg, duration, position, yOffset);
    }

    /**
     * 检查SDCard是否存在
     *
     * @return
     */
    public static boolean checkSDCardPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    }

    /**
     * 检查SDCard是否可读
     *
     * @return
     */
    public static boolean checkSDCardRead() {
        if (checkSDCardPresent())
            return Environment.getExternalStorageDirectory().canRead();
        else
            return false;
    }

    /**
     * 检查SDCard是否可写
     *
     * @return
     */
    public static boolean checkSDCardWriter() {
        if (checkSDCardPresent())
            return Environment.getExternalStorageDirectory().canWrite();
        else
            return false;
    }

    /**
     * 检查sdcard的剩余容量是否超过size
     *
     * @param size 单位是KB
     * @return
     */
    public static boolean checkSDCardCapacity(int size) {
        // 取得sdcard文件路径
        File pathFile = Environment.getExternalStorageDirectory();
        StatFs statfs = new StatFs(pathFile.getPath());
        // 获取SDCard上每个block的SIZE
        long blocSize = statfs.getBlockSize();
        // 获取可供程序使用的Block的数量
        long availaBlock = statfs.getAvailableBlocks();
        return (availaBlock * blocSize / 1024) > size;
    }

    /**
     * 检查sdcard中是否存在制定路径的文件
     *
     * @param path
     * @return
     */
    public static boolean checkSDCardFile(String path) {
        if (path == null || "".equals(path.trim()))
            return false;
        File file = new File(path);
        return file.exists();
    }




    /**
     * 保存数据库文件(注意这个方法比较耗时请在线程里使用)
     *
     * @param context
     * @param fileName
     * @return
     */
    public static boolean retrieveApkFromAssets(String fileName, Context context) {
        String outPathString = context.getDatabasePath("db").getAbsolutePath() + "/";
        outPathString = outPathString.substring(0, outPathString.length() - 3);
        return retrieveApkFromAssets(fileName, outPathString, context);
    }

    public static boolean retrieveApkFromAssets(String fileName, String outPathString,
                                                Context context) {
        boolean bRet = false;
		FileOutputStream fos=null;
        try (InputStream is = context.getAssets().open(fileName);){

			File file = new File(outPathString + "/" + fileName);
			file.createNewFile();
             fos = new FileOutputStream(file);

            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }

            fos.close();
            is.close();

            bRet = true;

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	if (fos!=null){
				try {
					fos.close();
				} catch (IOException e) {

				}
			}

		}

        return bRet;
    }

    /**
     * 隐藏软键盘
     *
     * @param activity 要隐藏软键盘的activity
     */
    public static void hideSoftKeyBoard(Activity activity) {
        if (activity == null || activity.getWindow() == null)
            return;
        final View v = activity.getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            try {
                ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void showSoftKeyBroad(Context context, EditText editText) {
        InputMethodManager mgr = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // only will trigger it if no physical keyboard is open
        mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 判断软键盘是否显示
     *
     * @param rootView 传当前页面的根布局（view.getRootView）
     */
    public static boolean isKeyBoardShown(View rootView) {
        final int softkeyboardheight = 100;
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - rect.bottom;
        return heightDiff > softkeyboardheight * dm.density;
    }

    /**
     * 判断当前页面软键盘是否是弹出状态，如果弹出则关闭
     */
    public static void hideSoftKeyBroadIfShow(final Activity mContext, long time) {
        final InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (time == 0) {
            manager.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (mContext.getCurrentFocus() != null && mContext.getCurrentFocus().getWindowToken() != null) {
                        manager.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }, time);
        }
    }

    /**
     * 隐藏软键盘 8.7.1
     *
     * @param context
     * @param view
     */
    public static void hideSoftKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }




    /**
     * 隐藏软键盘
     *
     * @param context
     * @param editText
     */
    public static void hideSoftKeyBroad(Context context, EditText editText) {
        InputMethodManager mgr = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 显示软键盘，和上面的showSoftKeyBroad方法的区别在于，如果从其他activity返回的时候需要延迟一点才能显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void showKeyBoardLater(final Context context, final EditText editText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showSoftKeyBroad(context, editText);
            }
        }, 500);
    }

    /**
     * 显示软键盘，和上面的showSoftKeyBroad方法的区别在于，如果从其他activity返回的时候需要延迟一点才能显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void showKeyBoardLater(final Context context, final EditText editText,
                                         long laterTime) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showSoftKeyBroad(context, editText);
            }
        }, laterTime);
    }

    /**
     * 本应用是否启动
     *
     * @param context
     * @param PackageName
     * @return
     */
    public static boolean isRuning(Context context, String PackageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        for (RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(PackageName)
                    && info.baseActivity.getPackageName().equals(PackageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 本应用是否启动
     *
     * @param context
     * @return
     */
    public static boolean isRuning(Context context) {
        return isRuning(context, context.getPackageName());
    }

    /**
     * 用来判断服务是否运行
     *
     * @param mContext
     * @param className 判断的服务名字
     * @return true在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            // UtilsLog.e("congjianfei",
            // "启动的服务有哪些呢？"+i+serviceList.get(i).service.getClassName());
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }



    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[4 * 1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }



    /*
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     *
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        try {
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            return gps || network;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 强制帮用户打开GPS(需要权限)
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开GPS设置
     */
    public static final void setGPS(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        try {
            activity.startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException ex) {
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                activity.startActivityForResult(intent, 0);
            } catch (Exception e) {
            }
        }
    }



    /**
     * cancel 异步任务
     */
    public static void cancelAsyncTask(AsyncTask... tasks) {
        if (tasks == null || tasks.length == 0)
            return;
        for (AsyncTask task : tasks) {
            if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
                task.cancel(true);
            }
        }
    }

    public static String SHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

   

    /**
     * 判断是否开启了录音权限
     */
    public static boolean isHasPermission(final Context context) {
        // 音频获取源
        int audioSource = MediaRecorder.AudioSource.MIC;
        // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
        int sampleRateInHz = 44100;
        // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
        int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
        // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        // 缓冲区字节大小
        int bufferSizeInBytes = 0;
        bufferSizeInBytes = 0;
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, audioFormat);
        AudioRecord audioRecord = new AudioRecord(audioSource, sampleRateInHz,
                channelConfig, audioFormat, bufferSizeInBytes);
        //开始录制音频
        try {
            // 防止某些手机崩溃，例如联想
            audioRecord.startRecording();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        /**
         * 根据开始录音判断是否有录音权限
         */
        if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
            return false;
        }
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;

        return true;
    }

    /**
     * Get activity from context object
     *
     * @param context something
     * @return object of Activity or null if it is not Activity
     */
    public static Activity getForActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return getForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static FragmentActivity getForFragmentActivity(Context context) {
        if (context == null) return null;
        if (context instanceof FragmentActivity) {
            return (FragmentActivity) context;
        }
        return null;
    }

    /**
     * 不需要申请存储权限 获得文件地址。
     *
     * @return
     */
    public static String getFileDir(String path) {
        String fileDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//判断外部存储是否可用
            fileDir = MyApplication.Companion.getSelf().getApplicationContext().getExternalFilesDir(null).getAbsolutePath()
                    + path;
            File externaleFile = new File(fileDir);
            if (!externaleFile.exists()) {
                externaleFile.mkdirs();
            }
        } else {
            fileDir = MyApplication.Companion.getSelf().getFilesDir().getAbsolutePath() + path;
        }
        return fileDir;
    }

    /**
     * 不需要申请存储权限 获得缓存地址。
     *
     * @return
     */
    public static String getCacheDir(String path) {
        String cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = MyApplication.Companion.getSelf().getApplicationContext().getExternalCacheDir().getAbsolutePath()
                    + path;
            if (!new File(cacheDir).exists()) {
                new File(cacheDir).mkdirs();
            }
        } else {
            cacheDir = MyApplication.Companion.getSelf().getCacheDir().getAbsolutePath() + path;
        }
        return cacheDir;
    }

    private static long lastClickTime = 0;
    private final static int SPACE_TIME = 500;

    /**
     * 防止连续点击方法
     *
     * @return
     */
    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime >
                SPACE_TIME) {
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        lastClickTime = currentTime;
        return isClick2;
    }
}