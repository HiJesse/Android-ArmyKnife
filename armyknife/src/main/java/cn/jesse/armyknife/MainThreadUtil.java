package cn.jesse.armyknife;

import android.os.Handler;
import android.os.Looper;

/**
 * 主线程相关工具
 * Created by jesse on 08/08/2017.
 */

public class MainThreadUtil {
    private Handler handler;
    private static MainThreadUtil mInstance;

    private MainThreadUtil() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public static synchronized MainThreadUtil getInstance() {
        if(mInstance == null) {
            mInstance = new MainThreadUtil();
        }
        return mInstance;
    }

    /**
     * 将runnable 对象post 进主线程执行
     * @param runnable 带执行对象
     */
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }

    /**
     * 当前运行进程是否为主进程
     * @return
     */
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
