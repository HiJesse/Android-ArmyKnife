package cn.jesse.armyknife;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * context 全局工具
 * Created by jesse on 14/08/2017.
 */

public class ContextUtil {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private ContextUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        ContextUtil.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("u should init first");
    }
}
