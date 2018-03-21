package date.bitman.utils_lib.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>Title:错误日记保存</p>
 * <p>Description:</p>
 *
 * @author 张禹
 *
 */

public class SaveCrashLog implements Thread.UncaughtExceptionHandler {
    private static SaveCrashLog mSaveCrashLog = null;
    private String mToast = "额~~主人,我现在死了一次了";
    private OnCrashHandler onCrashHandler;
    private Context mContext;
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    // 用来存储设备信息和异常信息
    private Map<String, String> mInfos = new TreeMap<>();
    private static final String CRASH_REPORTER_EXTENSION = ".log";
    private final String mPath = FileUtils.getRootPath("crash");

    private SaveCrashLog(){}

    public static SaveCrashLog getInstance(){
        if (mSaveCrashLog == null){
            mSaveCrashLog = new SaveCrashLog();
        }
        return mSaveCrashLog;
    }

    public static void initSaveCrash(Context context, String toastMsg,
                                     OnCrashHandler onCrashHandler){
        SaveCrashLog crashLog = getInstance();
        if (!TextUtils.isEmpty(toastMsg)){
            crashLog.mToast = toastMsg;
        }
        crashLog.mContext = context;
        crashLog.onCrashHandler = onCrashHandler;
        crashLog.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashLog);
    }

    public static void initSaveCrash(Context context,String toastMsg){
        initSaveCrash(context,toastMsg,null);
    }

    public static void initSaveCrash(Context context){
        initSaveCrash(context,null);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(t, e);
        } else {
            if(false){//暂不使用
                SystemClock.sleep(3000);
                Intent intent = new Intent(mContext.getApplicationContext(), null);
                PendingIntent restartIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                //进程死亡时调用系统级调用系统级服务重新开启应用
                AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            mDefaultHandler.uncaughtException(t, e);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    protected boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        if (onCrashHandler != null && onCrashHandler.onCrash(ex)) {
            return true;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                // Toast 显示需要出现在一个线程的消息队列中
                Looper.prepare();
                Toast.makeText(mContext, mToast, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //收集设备参数信息;保存日志文件;
        // 收集设备参数信息
        collectDeviceInfo();
        // 保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     */
    private void collectDeviceInfo() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfos.put("VersionName", versionName);
                mInfos.put("VersionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SaveCrashLog", "an error occured when collect package info",e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfos.put(field.getName(), field.get(null).toString());
                Log.e("SaveCrashLog", field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e("SaveCrashLog", "an error occured when collect crash info",e);
            }
        }
    }

    /**
     * 保存日志文件
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        sb.append(TimeUtil.ms2String(System
                .currentTimeMillis(),"yyyy-MM-dd HH:mm:ss") + System.getProperty("line.separator"));

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        //设备信息
        for (Map.Entry<String, String> entry : mInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + System.getProperty("line.separator"));
        }
        Log.e("SaveCrashLog", result);

        OutputStream os = null;
        String fileName = "错误日志" + "-" + System.currentTimeMillis()
                + CRASH_REPORTER_EXTENSION;
        File file = new File(mPath + fileName);
        try {
            if (!file.exists() && !file.createNewFile()) {
                Log.e("SaveCrashLog", "CrashHandler处理文件创建失败");
                return null;
            }

            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                os = new FileOutputStream(file);
                os.write(sb.toString().getBytes());
                os.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e("SaveCrashLog", "an error occured while writing file...",e);
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public interface OnCrashHandler {
        boolean onCrash(Throwable e);
    }
}