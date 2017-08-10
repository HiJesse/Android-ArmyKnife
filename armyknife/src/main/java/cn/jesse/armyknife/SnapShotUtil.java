package cn.jesse.armyknife;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

import cn.jesse.armyknife.exception.UnsupportedOperationException;

/**
 * 截图相关工具
 * Created by jesse on 10/08/2017.
 */

public class SnapshotUtil {

    private SnapshotUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity 当前view
     * @return bitmap
     */
    public static Bitmap snapshotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = DimensionUtil.getScreenWidth(activity);
        int height = DimensionUtil.getScreenHeight(activity);
        Bitmap bp;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity 当前view
     * @return bitmap
     */
    public static Bitmap snapshotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = DimensionUtil.getScreenWidth(activity);
        int height = DimensionUtil.getScreenHeight(activity);
        Bitmap bp;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }
}
