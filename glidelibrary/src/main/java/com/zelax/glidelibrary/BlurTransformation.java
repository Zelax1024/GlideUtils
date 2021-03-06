package com.zelax.glidelibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.RSRuntimeException;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.zelax.glidelibrary.gaussian_blur.FastBlur;
import com.zelax.glidelibrary.gaussian_blur.RSBlur;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * created by Zelax on 2021/2/5.
 */
public class BlurTransformation  extends BitmapTransformation {

    private static final int VERSION = 1;
    private static final String ID =
            "jp.wasabeef.glide.transformations.BlurTransformation." + VERSION;

    private static final int MAX_RADIUS = 25;
    private static final int DEFAULT_DOWN_SAMPLING = 1;

    private final int blur;//模糊度
    private final int sampling;
    private final Context context;
    public BlurTransformation(Context context) {
        this(context,MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }



    public BlurTransformation(Context context,int blur) {
        this(context,blur, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context,int blur, int sampling) {
        this.blur = blur;
        this.sampling = sampling;
        this.context=context;
    }

    @Override
    protected Bitmap transform( @NonNull BitmapPool pool,
                               @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;

        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

//        setCanvasBitmapDensity(toTransform, bitmap);
        bitmap.setDensity(toTransform.getDensity());

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTransform, 0, 0, paint);

        try {
            bitmap = RSBlur.blur(context, bitmap, blur);
        } catch (RSRuntimeException e) {
            bitmap = FastBlur.blur(bitmap, blur, true);
        }

        return bitmap;
    }

    @Override
    public String toString() {
        return "BlurTransformation(blur=" + blur + ", sampling=" + sampling + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BlurTransformation &&
                ((BlurTransformation) o).blur == blur &&
                ((BlurTransformation) o).sampling == sampling;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + blur * 1000 + sampling * 10;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + blur + sampling).getBytes(CHARSET));
    }
}
