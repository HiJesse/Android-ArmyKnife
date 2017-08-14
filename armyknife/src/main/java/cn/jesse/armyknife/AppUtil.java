package cn.jesse.armyknife;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

import cn.jesse.armyknife.exception.UnsupportedOperationException;

/**
 * app 相关工具
 * Created by jesse on 14/08/2017.
 */

public class AppUtil {
    private static final String TAG = PlatformUtil.class.getSimpleName();

    private AppUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断是否为UI主进程
     *
     * @param context context
     * @return 是否为主进程
     */
    public static boolean isAppMainProcess(Context context) {
        boolean isAppMainProcess = false;
        int pid = android.os.Process.myPid();
        String process = getAppNameByPID(context.getApplicationContext(), pid);

        if (getPackageName(context).equals(process)) {
            isAppMainProcess = true;
        }

        return isAppMainProcess;
    }

    /**
     * 根据Pid得到进程名
     *
     * @param context context
     * @param pid     进程id
     * @return 进程名
     */
    public static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context
                .getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }

    /**
     * 获取包名
     *
     * @param context context
     * @return 包名
     */
    public static String getPackageName(Context context) {
        if (context != null) {
            return context.getPackageName();
        }
        return "";
    }

    /**
     * 判断是否是debug
     *
     * @param context context
     * @return true /false
     */
    public static boolean isDebugVersion(Context context) {
        boolean isDebug = false;
        if (null == context) {
            return false;
        }
        String pkgName = getPackageName(context);
        PackageInfo packageInfo = null;
        try {

            packageInfo = context.getPackageManager().getPackageInfo(
                    pkgName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        if (packageInfo != null) {
            ApplicationInfo info = packageInfo.applicationInfo;
            isDebug = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
        return isDebug;
    }

    /**
     * 获取应用version name
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "-1";
        PackageManager manager = context.getApplicationContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return versionName;
    }

    /**
     * 获取应用version code
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        PackageManager manager = context.getApplicationContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return versionCode;
    }

    /**
     * 判断当前应用是否在后台运行
     *
     * @param context context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }
}
