package com.zelax.glidelibrary.progress;

import android.content.Context;

/**
 * created by Zelax on 2021/2/4.
 */
public class DensityUtils {
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
    public static int dp2px(Context context, float dp) {
        return (int) (getDensity(context) * dp + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        return (int) (px / getDensity(context) + 0.5f);
    }

    public static int sp2px(Context context, float sp) {
        return (int) (getFontDensity(context) * sp + 0.5f);
    }
    public static float getFontDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

}
