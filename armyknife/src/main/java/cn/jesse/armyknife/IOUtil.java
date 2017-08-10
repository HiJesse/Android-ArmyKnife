package cn.jesse.armyknife;

import android.util.Log;

import java.io.Closeable;

import cn.jesse.armyknife.exception.UnsupportedOperationException;

/**
 * IO相关工具
 * Created by jesse on 16/07/2017.
 */

public class IOUtil {
    private static final String TAG = IOUtil.class.getSimpleName();

    private IOUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 正确关闭实现了Closeable的对象
     *
     * @param closeable 要关闭的对象
     */
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
