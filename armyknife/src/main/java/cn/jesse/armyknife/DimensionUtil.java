package cn.jesse.armyknife;

import android.content.Context;

/**
 * dimension相关工具
 * 方法在强转int时都加了0.5f的偏移量, 目的是做出四舍五入的效果
 * Created by jesse on 09/08/2017.
 */

public class DimensionUtil {

    private DimensionUtil() {
        // unused
    }

    /**
     * 转化px为dp
     * @param context context
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 转化dp为px
     * @param context context
     * @param dipValue dp值
     * @return px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 转化px为sp
     * @param context context
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 转换sp为px
     * @param context context
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}