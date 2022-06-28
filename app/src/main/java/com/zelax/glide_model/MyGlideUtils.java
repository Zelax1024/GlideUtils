package com.zelax.glide_model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.zelax.glidelibrary.BlurTransformation;
import com.zelax.glidelibrary.progress.CircleProgressView;
import com.zelax.glidelibrary.progress.GlideApp;
import com.zelax.glidelibrary.progress.OnProgressListener;
import com.zelax.glidelibrary.progress.ProgressManager;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * created by Zelax on 2021/2/4.
 */
public class MyGlideUtils {
    /**
     * 图片加载带进度
     *
     * @param mContext
     * @param iv
     * @param url
     * @param circleProgressView
     */
    public static void loadProgressImg(Context mContext, ImageView iv, String url, CircleProgressView circleProgressView,int round) {
        ProgressManager.addListener(url, (isComplete, percentage, bytesRead, totalBytes) -> {
            Log.i("GlideUtils", "isComplete：" + isComplete + "---percentage：" + percentage + "---bytesRead：" + bytesRead + "---totalBytes：" + totalBytes);
            if (isComplete) {
                circleProgressView.setVisibility(View.GONE);
            } else {
                circleProgressView.setVisibility(View.VISIBLE);
                circleProgressView.setProgress(percentage);

            }
        });

//        GlideApp.with(mContext).load(url)
//                .setRound(round)
//                .addListener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        OnProgressListener onProgressListener = ProgressManager.getProgressListener(url);
//                        if (onProgressListener != null) {
//                            onProgressListener.onProgress(true, 100, 0, 0);
//                            ProgressManager.removeListener(url);
//                        }
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        OnProgressListener onProgressListener = ProgressManager.getProgressListener(url);
//                        if (onProgressListener != null) {
//                            onProgressListener.onProgress(true, 100, 0, 0);
//                            ProgressManager.removeListener(url);
//                        }
//                        return false;
//                    }
//                }).into(iv);


        GlideApp.with(mContext)

                .load(url)
                .setRound(round)
                .into(new DrawableImageViewTarget(iv) {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                OnProgressListener onProgressListener = ProgressManager.getProgressListener(url);
                if (onProgressListener != null) {
                    onProgressListener.onProgress(true, 100, 0, 0);
                    ProgressManager.removeListener(url);
                }
                super.onLoadFailed(errorDrawable);
            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                OnProgressListener onProgressListener = ProgressManager.getProgressListener(url);
                if (onProgressListener != null) {
                    onProgressListener.onProgress(true, 100, 0, 0);
                    ProgressManager.removeListener(url);
                }
                super.onResourceReady(resource, transition);
            }

            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
            }
        });
    }


    public static void loadCircleImg(Context mContext, ImageView iv, String url) {
        GlideApp.with(mContext).load(url).circleImg().into(iv);
    }


    /**
     * 加载高斯模糊图片
     * @param mContext
     * @param iv
     * @param url
     * @param blur 模糊度
     */
    public static void loadBlurImg(Context mContext,ImageView iv,String url,int blur){
        RequestOptions options = new RequestOptions();
        RequestOptions transform = options.transform(new BlurTransformation(mContext, blur));
        GlideApp.with(mContext).applyDefaultRequestOptions(transform).load(url).into(iv);
    }


    public static void loadNormalImg(Context mContext,ImageView iv,String url){
        GlideApp.with(mContext)
                .load(url)
                .into(iv);
    }

    public static void loadRoundImg(Context mContext, ImageView iv, String url, int radius, @DrawableRes int res){
        GlideApp.with(mContext)
                .load(url)
                .centerCrop()
                .setRound(radius)
                .placeholder(res)
                .into(iv);
    }

}
