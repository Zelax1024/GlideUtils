package com.zelax.glidelibrary;

import android.annotation.SuppressLint;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;

/**
 * created by Zelax on 2021/2/5.
 */
@GlideExtension
public class RoundTransform {

    private RoundTransform() {
    }

    @GlideOption
    public static BaseRequestOptions<?> setRound(BaseRequestOptions<?> options, int radius) {
        return options.transform(new GranularRoundedCorners(radius, radius, radius, radius));
    }
}
